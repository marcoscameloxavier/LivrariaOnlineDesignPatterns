package pucpr.livraria.repositorio;

import pucpr.livraria.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio {
    private ArrayList<Cliente> clientes;

    public ClienteRepositorio(){
        this.clientes = new ArrayList<>();
        this.clientes.add(new Cliente("João de Carvalho", "joao@gmail.com", "9999-9999", "Rua 25 de março, Rio de Janeiro", "111.111.111-11", "51030-250"));
        this.clientes.add(new Cliente("Maria da Silva", "maria@gmail.com", "8888-8888", "Avenida Jacarandá, lt 14, Águas Claras", "222.222.222-22", "71050-360"));
        this.clientes.add(new Cliente("José Pereira de Azevedo", "jose.azevedo@gmail.com", "(61) 99347-9582", "CLSW QD 105 Bloco A Loja 01, St. Sudoeste", "123.456.789-00", "70670-431"));
    }

    public Cliente buscarClientePorCPF(String cpf) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }

    public ArrayList<Cliente> getClientes() {
        return this.clientes;
    }

    public void atualizarCliente(Cliente cliente) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cliente.getCpf())) {
                c.setNome(cliente.getNome());
                c.setEmail(cliente.getEmail());
                c.setTelefone(cliente.getTelefone());
                c.setEndereco(cliente.getEndereco());
                c.setCep(cliente.getCep());
                c.setListaInteresses(cliente.getListaInteresses());
            }
        }
    }

    public Cliente salvar(Cliente cliente) {
        this.clientes.add(cliente);
        return cliente;
    }
}
