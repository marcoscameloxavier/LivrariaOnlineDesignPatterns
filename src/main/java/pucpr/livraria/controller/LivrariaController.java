package pucpr.livraria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pucpr.livraria.dto.LivroEmailDTO;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.ListaInteresses;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.facade.LivrariaFachada;

import java.util.List;

@RestController
public class LivrariaController {
    @Autowired
    private LivrariaFachada livrariaFachada;

    @GetMapping("/livros/titulo")
    public ResponseEntity<List<Livro>> getLivrosPorTitulo(String titulo) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorTitulo(titulo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/autor")
    public ResponseEntity<List<Livro>> getLivrosPorAutor(String autor) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorAutor(autor);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/genero")
    public ResponseEntity<List<Livro>> getLivrosPorGenero(String genero) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorGenero(genero);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = livrariaFachada.buscarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/clientes/cpf")
    public ResponseEntity<Cliente> getClientePorCPF(String cpf) {
        Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/clientes/listaInteresses")
    public ResponseEntity<Cliente> setListaInteressesCliente(String cpf, String genero, String autor) {
        Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
        cliente.setListaInteresses(new ListaInteresses(genero, autor));
        this.livrariaFachada.atualizarCliente(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/clientes/listaInteresses/{cpf}")
    public SseEmitter receberLivosRecomendados(@PathVariable String cpf){
        SseEmitter emitter = new SseEmitter();
        new Thread(() -> {
            try {
                Cliente cliente = livrariaFachada.buscarClientePorCPF(cpf);
                if(cliente!= null) {
                    ListaInteresses interesses = cliente.getListaInteresses();
                    if(interesses == null) {
                        emitter.send("Cliente sem interesses cadastrados");
                    }
                    else{
                        if(interesses.getAutor() != null && !interesses.getAutor().equals(" ")) {
                            List<Livro> livrosAutor = livrariaFachada.buscarLivrosPorAutor(interesses.getAutor());
                            enviarEmailCliente(emitter, livrosAutor);
                        }
                        if(interesses.getGenero() != null && !interesses.getGenero().equals(" ")){
                            List<Livro> livrosGenero = livrariaFachada.buscarLivrosPorGenero(interesses.getGenero());
                            enviarEmailCliente(emitter, livrosGenero);
                        }
                    }

                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }

    private void enviarEmailCliente(SseEmitter emitter, List<Livro> livrosGenero) {
        livrosGenero.forEach(livro -> {
            try {
                LivroEmailDTO email = new LivroEmailDTO(livro.getTitulo(), livro.getPreco(), livro.getPreviewLink());
                emitter.send(email);
                Thread.sleep(1000);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
    }
}
