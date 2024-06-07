package pucpr.livraria.dao;

import pucpr.livraria.entity.Pedido;
import pucpr.livraria.repositorio.PedidoRepositorio;

import java.util.ArrayList;

public class PedidoDAO {

    private PedidoRepositorio repositorio;
    public PedidoDAO() {
        this.repositorio = new PedidoRepositorio();
    }

    public void inserirPedido(Pedido pedido) {
        this.repositorio.adicionarPedido(pedido);
    }

    public ArrayList<Pedido> recuperarPedidos (){
        return this.repositorio.recuperarPedidos();
    }
}
