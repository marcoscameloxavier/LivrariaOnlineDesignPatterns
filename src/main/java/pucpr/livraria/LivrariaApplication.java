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
import pucpr.livraria.strategy.EntregaEconomica;
import pucpr.livraria.strategy.EntregaRapida;
import pucpr.livraria.strategy.EntregaSedex;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LivrariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaApplication.class, args);

		LivrariaFachada fachada = new LivrariaFachada();

		// Padrão Factory Method
		System.out.println("\n------Exemplo de uso do padrão Factory Method:------");

		Cliente cliente = new Cliente("João", "joao@example.com", "(61)99314-8541", "Avenida das Nações, Bloco B, Casa 20", "123.456.789-00", "12345-678");
		cliente = fachada.cadastrarCliente(cliente);
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
		System.out.println("\n------Exemplo de uso do padrão Facade, DAO e Singleton(dados da API):------");

		System.out.println("Buscando livros pelo título 'Design Patterns':");
		List<Livro> livros = fachada.buscarLivrosPorTitulo("Design Patterns");

		for (Livro livro : livros) {
			System.out.println("Título: " + livro.getTitulo());
			System.out.printf("Preço: R$%.2f com %d páginas\n",livro.getPreco(), livro.getNumPaginas());
		}

		// Padrão Chain of Responsibility
		System.out.println("\n------Exemplo de uso do padrão Chain of Responsibility + Strategy(cálculo do frete):------");

		System.out.println("\nCriando pedido para o cliente " + cliente.getNome() + " com Entrega Econômica:");
		Pedido pedido = fachada.criarPedido(cliente, (ArrayList<Livro>)livros, new EntregaEconomica());

		ProcessamentoPedido acompanhamentoPedido = LivrariaFachada.getChainOfResponsibility();
		acompanhamentoPedido.statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);

		System.out.println("\nDemonstração do mesmo pedido com Entrega Rápida:");
		pedido = fachada.criarPedido(cliente, (ArrayList<Livro>)livros, new EntregaRapida());
		acompanhamentoPedido.statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);

		System.out.println("\nDemonstração do mesmo pedido com Entrega Sedex:");
		pedido = fachada.criarPedido(cliente, (ArrayList<Livro>)livros, new EntregaSedex());

		acompanhamentoPedido.statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);
	}
}
