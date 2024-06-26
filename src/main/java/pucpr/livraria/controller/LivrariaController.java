package pucpr.livraria.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pucpr.livraria.dto.LivroEmailDTO;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.ListaInteresses;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.entity.Pedido;
import pucpr.livraria.facade.LivrariaFachada;
import pucpr.livraria.notificacao.Notificacao;
import pucpr.livraria.notificacao.NotificacaoFactory;
import pucpr.livraria.notificacao.NotificacaoRequest;
import pucpr.livraria.notificacao.TipoNotificacao;
import pucpr.livraria.processamentoLote.PedidoConcluidoConsumer;
import pucpr.livraria.processamentoLote.PedidoConcluidoProducer;
import pucpr.livraria.processamentoLote.PedidoConsumer;
import pucpr.livraria.processamentoLote.PedidoProducer;
import pucpr.livraria.dto.PedidoDTO;
import pucpr.livraria.processamentoPedido.ProcessamentoPedido;
import pucpr.livraria.strategy.EntregaEconomica;
import pucpr.livraria.strategy.EntregaRapida;
import pucpr.livraria.strategy.EntregaStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@RestController
public class LivrariaController {
    @Autowired
    private LivrariaFachada livrariaFachada;

    @Autowired
    private PedidoConcluidoProducer pedidoConcluidoProducer;

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final BlockingQueue<Pedido> pedidoQueue = new LinkedBlockingQueue<>();
    private final PedidoProducer pedidoProducer = new PedidoProducer(pedidoQueue);
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void init() {
        PedidoConsumer pedidoConsumer = new PedidoConsumer(pedidoQueue, livrariaFachada);
        Thread consumerThread = new Thread(pedidoConsumer);
        consumerThread.start();

        // Configurar o consumer de pedidos concluídos
        PedidoConcluidoConsumer pedidoConcluidoConsumer = new PedidoConcluidoConsumer(pedidoConcluidoProducer.getQueue(), emitters);
        Thread consumerConcluidoThread = new Thread(pedidoConcluidoConsumer);
        consumerConcluidoThread.start();
    }

    @GetMapping("/livros/titulo")
    public ResponseEntity<List<Livro>> getLivrosPorTitulo(@RequestParam String titulo) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorTitulo(titulo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/autor")
    public ResponseEntity<List<Livro>> getLivrosPorAutor(@RequestParam String autor) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorAutor(autor);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/genero")
    public ResponseEntity<List<Livro>> getLivrosPorGenero(@RequestParam String genero) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorGenero(genero);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = livrariaFachada.buscarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/clientes/cpf")
    public ResponseEntity<Cliente> getClientePorCPF(@RequestParam String cpf) {
        Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes/listaInteresses")
    public ResponseEntity<Cliente> setListaInteressesCliente(@RequestParam String cpf, @RequestParam String genero, @RequestParam String autor) {
        Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
        cliente.setListaInteresses(new ListaInteresses(genero, autor));
        this.livrariaFachada.atualizarCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/clientes/listaInteresses/{cpf}")
    public SseEmitter receberLivosRecomendados(@PathVariable String cpf) {
        SseEmitter emitter = new SseEmitter();
        executor.execute(() -> {
            try {
                Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
                if (cliente != null) {
                    ListaInteresses interesses = cliente.getListaInteresses();
                    if (interesses == null) {
                        emitter.send("Cliente sem interesses cadastrados");
                    } else {
                        enviarRecomendacoes(emitter, interesses);
                    }
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });
        return emitter;
    }

    private void enviarRecomendacoes(SseEmitter emitter, ListaInteresses interesses) throws IOException, InterruptedException {
        if (interesses.getAutor() != null && !interesses.getAutor().isBlank()) {
            List<Livro> livrosAutor = livrariaFachada.buscarLivrosPorAutor(interesses.getAutor());
            enviarBlocosDeLivros(emitter, livrosAutor);
        }
        if (interesses.getGenero() != null && !interesses.getGenero().isBlank()) {
            List<Livro> livrosGenero = livrariaFachada.buscarLivrosPorGenero(interesses.getGenero());
            enviarBlocosDeLivros(emitter, livrosGenero);
        }
    }

    private void enviarBlocosDeLivros(SseEmitter emitter, List<Livro> livros) throws IOException, InterruptedException {
        int blocoTamanho = 4;
        for (int i = 0; i < livros.size(); i += blocoTamanho) {
            int fim = Math.min(i + blocoTamanho, livros.size());
            List<Livro> bloco = livros.subList(i, fim);
            LivroEmailDTO[] emailBloco = bloco.stream()
                    .map(livro -> new LivroEmailDTO(livro.getTitulo(), livro.getPreco(), livro.getPreviewLink(), livro.getCapa()))
                    .toArray(LivroEmailDTO[]::new);
            emitter.send(emailBloco);
            Thread.sleep(1000); // Simular tempo de envio de e-mail
        }
    }

    @PostMapping("/enviarNotificacao")
    public ResponseEntity<?> enviarNotificacao(@RequestBody NotificacaoRequest notificacaoRequest) {
        Cliente cliente = notificacaoRequest.getCliente();
        String mensagem = notificacaoRequest.getMensagem();
        TipoNotificacao tipo = notificacaoRequest.getTipoNotificacao();

        NotificacaoFactory factory = NotificacaoFactory.getFactory(tipo);
        Notificacao notificacao = factory.criarNotificacao();
        String mensagemEnviada = notificacao.enviar(mensagem, cliente);

        return ResponseEntity.ok(Collections.singletonMap("resultado", "Notificação enviada via " + tipo.name().toLowerCase() + ".<br/>" + mensagemEnviada));
    }

    @GetMapping("/livros/{query}")
    public ResponseEntity<List<Livro>> getLivros(@PathVariable String query) {
        List<Livro> livros = livrariaFachada.buscarLivros(query);
        return ResponseEntity.ok(livros);
    }

    @PostMapping("/enviarPedido")
    public ResponseEntity<Pedido> enviarPedido(@RequestBody PedidoDTO pedidoRequest) {
        Cliente cliente = livrariaFachada.buscarClientePorCPF(pedidoRequest.getCpf());
        Pedido pedido = livrariaFachada.criarPedido(cliente, (ArrayList<Livro>) pedidoRequest.getLivros(), pedidoRequest.getEntrega());
        cliente.addPedido(pedido);
        return ResponseEntity.ok(pedido);
    }

    @PostMapping("/processarPedido")
    public ResponseEntity<Map<String, String>> processarPedido(@RequestBody Map<String, String> request) {
        String cpf = request.get("cpf");
        Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
        List<Pedido> pedidos = cliente.getPedidos(); // assumindo que Cliente tem uma lista de pedidos
        Pedido pedido = pedidos.get(pedidos.size() - 1); // pega o último pedido para processar
        String tipoEntrega;
        EntregaStrategy entregaStrategy = pedido.getEntregaStrategy();
        // comparar o tipo da classe da EntregaStrategy para retornar a String correspondente
        if (entregaStrategy instanceof EntregaRapida) {
            tipoEntrega = "Entrega Rápida";
        } else if (entregaStrategy instanceof EntregaEconomica) {
            tipoEntrega = "Entrega Econômica";
        } else {
            tipoEntrega = "Entrega Sedex";
        }

        ProcessamentoPedido acompanhamentoPedido = livrariaFachada.getChainOfResponsibility();
        String processamento = acompanhamentoPedido.statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);

        return ResponseEntity.ok(Collections.singletonMap("resultado", "Pedido de " + tipoEntrega + " processado: <br/>" + processamento));
    }

    @PostMapping("/addPedidoFila")
    public ResponseEntity<?> addPedidoFila(@RequestBody PedidoDTO pedidoRequest) {
        try {
        Cliente cliente = livrariaFachada.buscarClientePorCPF(pedidoRequest.getCpf());
        Pedido pedido = livrariaFachada.criarPedido(cliente, (ArrayList<Livro>) pedidoRequest.getLivros(), pedidoRequest.getEntrega());
        cliente.addPedido(pedido);
        pedidoProducer.addOrder(pedido);
        return ResponseEntity.ok(pedido);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar pedido à fila.");
        }
    }

    @GetMapping("/pedidos/processados")
    public SseEmitter getPedidosProcessados() {
        // Aumentando o tempo limite para 30 minutos (1800000 milissegundos)
        SseEmitter emitter = new SseEmitter(1800000L);
        emitters.add(emitter);
        executor.execute(() -> {
            try {
                emitter.onCompletion(() -> emitters.remove(emitter));
                emitter.onTimeout(() -> emitters.remove(emitter));

                // Enviando "keep-alive" a cada 25 minutos para evitar timeouts
                while (true) {
                    Thread.sleep(1500000); // 25 minutos
                    emitter.send(SseEmitter.event()
                            .comment("keep-alive")
                            .id(String.valueOf(System.currentTimeMillis()))
                            .data("", MediaType.TEXT_PLAIN));
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}
