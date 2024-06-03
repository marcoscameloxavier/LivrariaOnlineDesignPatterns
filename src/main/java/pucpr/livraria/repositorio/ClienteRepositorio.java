package pucpr.livraria.repositorio;

import pucpr.livraria.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio {
    private List<Cliente> clientes;

    public ClienteRepositorio(){
        this.clientes = new ArrayList<>();
        this.clientes.add(new Cliente("João", "joao@gmail.com", "9999-9999", "Rua 1", "111.111.111-11", "80000-000"));
    }
}
