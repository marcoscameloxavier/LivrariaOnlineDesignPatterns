package pucpr.livraria.dao;

import pucpr.livraria.entity.Cliente;
import pucpr.livraria.repositorio.ClienteRepositorio;

import java.util.List;

public class ClienteDAO {

    private ClienteRepositorio repositorioClientes;
    public ClienteDAO() {
        this.repositorioClientes = new ClienteRepositorio();
    }


    public Cliente buscarClientePorCPF(String cpf) {
        return repositorioClientes.buscarClientePorCPF(cpf);
    }

    public List<Cliente> buscarClientes() {
        return this.repositorioClientes.getClientes();
    }

    public void atualizarCliente(Cliente cliente) {
        this.repositorioClientes.atualizarCliente(cliente);
    }
}
