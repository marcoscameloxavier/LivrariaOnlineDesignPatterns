package pucpr.livraria.facade;

import org.springframework.stereotype.Service;
import pucpr.livraria.dao.ClienteDAO;
import pucpr.livraria.dao.LivroDAO;
import pucpr.livraria.dao.PedidoDAO;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.entity.Pedido;
import pucpr.livraria.processamentoPedido.*;
import pucpr.livraria.strategy.EntregaStrategy;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivrariaFachada {
    private LivroDAO livroDAO;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;

    public LivrariaFachada() {
        this.livroDAO = new LivroDAO();
        this.pedidoDAO = new PedidoDAO();
        this.clienteDAO = new ClienteDAO();
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteDAO.salvar(cliente);
    }

    public void realizarPedido(Pedido pedido) {
       // pedidoDAO.salvar(pedido);
        // Outras operações relacionadas
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
        return livroDAO.buscarLivrosPorAutor(autor);
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        return livroDAO.buscarLivrosPorTitulo(titulo);
    }

    public List<Livro> buscarLivrosPorGenero(String genero) {
        return livroDAO.buscarLivrosPorGenero(genero);
    }

    public List<Livro> buscarLivros(String query) {
        return livroDAO.buscarLivros(query);
    }

    public Pedido criarPedido(Cliente cliente, ArrayList<Livro> listaLivros, EntregaStrategy tipoEntrega) {
        Pedido pedido = new Pedido(cliente, listaLivros);
        pedido.setEntregaStrategy(tipoEntrega);
        this.pedidoDAO.inserirPedido(pedido);
        return pedido;
    }

    public ArrayList<Pedido> recuperarPedidos() {
        return this.pedidoDAO.recuperarPedidos();
    }

    public static ProcessamentoPedido getChainOfResponsibility() {

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

    public Cliente buscarClientePorCPF(String cpf) {
        return clienteDAO.buscarClientePorCPF(cpf);
    }

    public List<Cliente> buscarClientes() {
        return clienteDAO.buscarClientes();
    }

    public void atualizarCliente(Cliente cliente) {
        clienteDAO.atualizarCliente(cliente);
    }
}
