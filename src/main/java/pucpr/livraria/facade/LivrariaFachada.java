package pucpr.livraria.facade;

import pucpr.livraria.dao.ClienteDAO;
import pucpr.livraria.dao.LivroDAO;
import pucpr.livraria.dao.PedidoDAO;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.entity.Pedido;

import java.util.List;

public class LivrariaFachada {
    private LivroDAO livroDAO;
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;

    public LivrariaFachada() {
        this.livroDAO = new LivroDAO();
        this.pedidoDAO = new PedidoDAO();
        this.clienteDAO = new ClienteDAO();
    }

    public void cadastrarCliente(Cliente cliente) {
        //clienteDAO.salvar(cliente);
    }

    public void realizarPedido(Pedido pedido) {
       // pedidoDAO.salvar(pedido);
        // Outras operações relacionadas
    }

    public List<Livro> buscarLivrosPorAutor(String autor) {
       // return livroDAO.buscarPorAutor(autor);
        return livroDAO.buscarLivros(autor);
    }
}
