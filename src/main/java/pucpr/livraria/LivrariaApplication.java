package pucpr.livraria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.facade.LivrariaFachada;
import pucpr.livraria.notificacao.ConcreteNotificacaoFactory;
import pucpr.livraria.notificacao.Notificacao;
import pucpr.livraria.notificacao.NotificacaoFactory;
import pucpr.livraria.notificacao.TipoNotificacao;

@SpringBootApplication
public class LivrariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaApplication.class, args);
		//Exemplo de uso do padrão Factory Method

		//Cliente(String nome, String email, String telefone, String endereco, String cpf, String senha)
		Cliente cliente = new Cliente("João", "joao@example.com", "(61)99314-8541", "Rua A, 123", "123.456.789-00", "12345-678");
		NotificacaoFactory factory = new ConcreteNotificacaoFactory();

		Notificacao notificacaoEmail = factory.criarNotificacao(TipoNotificacao.EMAIL);
		notificacaoEmail.enviar("Bem-vindo à nossa livraria!", cliente);

		Notificacao notificacaoSMS = factory.criarNotificacao(TipoNotificacao.SMS);
		notificacaoSMS.enviar("Seu pedido foi enviado.", cliente);

		Notificacao notificacaoWhatsApp = factory.criarNotificacao(TipoNotificacao.WHATSAPP);
		notificacaoWhatsApp.enviar("Seu pedido será entregue amanhã.", cliente);


		// Uso do DAO
		LivrariaFachada fachada = new LivrariaFachada();
		//Tá dando erro, precisa investigar esse código
		/*List<Livro> livros = fachada.buscarLivrosPorAutor("Design Patterns");

		for (Livro livro : livros) {
			System.out.println("Título: " + livro.getTitulo());
			// Outros atributos do livro
		}*/
	}

}
