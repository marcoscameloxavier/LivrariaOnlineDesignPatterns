package pucpr.livraria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.entity.Pedido;
import pucpr.livraria.facade.LivrariaFachada;
import pucpr.livraria.notificacao.ConcreteNotificacaoFactory;
import pucpr.livraria.notificacao.Notificacao;
import pucpr.livraria.notificacao.NotificacaoFactory;
import pucpr.livraria.notificacao.TipoNotificacao;
import pucpr.livraria.processamentoPedido.*;

import java.util.List;

@SpringBootApplication
public class LivrariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaApplication.class, args);
		//Exemplo de uso do padrão Factory Method

		//Cliente(String nome, String email, String telefone, String endereco, String cpf, String senha)
		Cliente cliente = new Cliente("João", "joao@example.com", "(61)99314-8541", "Rua A, 123", "123.456.789-00", "12345-678");
		NotificacaoFactory factory = new ConcreteNotificacaoFactory();

	//tirar a dúvida com professor se isso é abstract factory ou o factory method?
	//	Notificacao notificacaoEmail = factory.criarNotificacao(TipoNotificacao.EMAIL);
	//	notificacaoEmail.enviar("Bem-vindo à nossa livraria!", cliente);

		factory.criarNotificacao(TipoNotificacao.EMAIL).enviar("Bem-vindo à nossa livraria!", cliente);

		Notificacao notificacaoSMS = factory.criarNotificacao(TipoNotificacao.SMS);
		notificacaoSMS.enviar("Seu pedido foi enviado.", cliente);

		Notificacao notificacaoWhatsApp = factory.criarNotificacao(TipoNotificacao.WHATSAPP);
		notificacaoWhatsApp.enviar("Seu pedido será entregue amanhã.", cliente);


		// Uso do DAO
		LivrariaFachada fachada = new LivrariaFachada();
		//Tá dando erro, precisa investigar esse código
		List<Livro> livros = fachada.buscarLivrosPorAutor("Design");

		for (Livro livro : livros) {
			System.out.println("Título: " + livro.getTitulo());
			// Outros atributos do livro
		}

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
