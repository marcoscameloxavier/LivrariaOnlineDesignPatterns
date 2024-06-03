package pucpr.livraria.dao;

import pucpr.livraria.entity.Cliente;
import pucpr.livraria.repositorio.ClienteRepositorio;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private ClienteRepositorio clientes;
    public ClienteDAO() {
        this.clientes = new ClienteRepositorio();
    }


}
