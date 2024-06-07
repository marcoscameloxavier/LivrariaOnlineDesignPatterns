package pucpr.livraria.facade;

import org.springframework.stereotype.Service;
import pucpr.livraria.dao.ClienteDAO;
import pucpr.livraria.dao.LivroDAO;
import pucpr.livraria.dao.PedidoDAO;
import pucpr.livraria.entity.Cliente;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.entity.Pedido;

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

    public void cadastrarCliente(Cliente cliente) {
        //clienteDAO.salvar(cliente);
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

    public Pedido criarPedido(Cliente cliente, ArrayList<Livro> listaLivros){
        Pedido pedido = new Pedido(cliente, listaLivros);
        this.pedidoDAO.inserirPedido(pedido);
        return pedido;
    }

    public ArrayList<Pedido> recuperarPedidos() {
        return this.pedidoDAO.recuperarPedidos();
    }

}
