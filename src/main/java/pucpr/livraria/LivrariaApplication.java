package pucpr.livraria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.entity.Pedido;
import pucpr.livraria.facade.LivrariaFachada;
import pucpr.livraria.notificacao.NotificacaoFactory;
import pucpr.livraria.notificacao.Notificacao;
import pucpr.livraria.notificacao.TipoNotificacao;
import pucpr.livraria.processamentoPedido.*;

import java.util.List;

@SpringBootApplication
public class LivrariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaApplication.class, args);

		// Padrão Factory Method
		System.out.println("\n------Exemplo de uso do padrão Factory Method:------");

		Cliente cliente = new Cliente("João", "joao@example.com", "(61)99314-8541", "Rua A, 123", "123.456.789-00", "12345-678");
		NotificacaoFactory factory = NotificacaoFactory.getFactory(TipoNotificacao.EMAIL);
		Notificacao notificacao = factory.criarNotificacao();

		notificacao.enviar("Bem-vindo à nossa livraria!", cliente);
		System.out.println("Mensagem enviada por email.\n");

		Notificacao notificacaoSMS = NotificacaoFactory.getFactory(TipoNotificacao.SMS).criarNotificacao();
		notificacaoSMS.enviar("Seu pedido foi enviado.", cliente);
		System.out.println("Mensagem enviada por SMS.\n");

		Notificacao notificacaoWhatsApp = NotificacaoFactory.getFactory(TipoNotificacao.WHATSAPP).criarNotificacao();;
		notificacaoWhatsApp.enviar("Seu pedido será entregue amanhã.", cliente);
		System.out.println("Mensagem enviada por WhatsApp.\n");

		// Padrões Facade e DAO
		System.out.println("\n------Exemplo de uso do padrão Facade e DAO:------");
		LivrariaFachada fachada = new LivrariaFachada();
		List<Livro> livros = fachada.buscarLivrosPorAutor("Design");

		for (Livro livro : livros) {
			System.out.println("Título: " + livro.getTitulo());
		}

		// Padrão Chain of Responsibility
		System.out.println("\n------Exemplo de uso do padrão Chain of Responsibility:------");
		Pedido pedido = new Pedido();
		ProcessamentoPedido acompanhamentoPedido = getChainOfResponsibility();
		acompanhamentoPedido.statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);
	}

	// Perguntar ao professor qual o melhor local para colocar esse método
	private static ProcessamentoPedido getChainOfResponsibility() {

		ProcessamentoPedido pagamentoEfetuado = new PagamentoPedido();
		ProcessamentoPedido emissaoNF = new EmissaoNF();
		ProcessamentoPedido entregaTransportador = new EntregaTransportador();
		ProcessamentoPedido saidaEntrega = new SaidaEntrega();
		ProcessamentoPedido entregaRealizada = new EntregaRealizada();

		pagamentoEfetuado.setProximo(emissaoNF);
		emissaoNF.setProximo(entregaTransportador);
		entregaTransportador.setProximo(saidaEntrega);
		saidaEntrega.setProximo(entregaRealizada);

		return pagamentoEfetuado;
	}

}
