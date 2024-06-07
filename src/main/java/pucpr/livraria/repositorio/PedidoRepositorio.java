package pucpr.livraria.repositorio;

import pucpr.livraria.entity.Pedido;

import java.util.ArrayList;


public class PedidoRepositorio {
    private ArrayList<Pedido> pedidos;

    public PedidoRepositorio(){
        this.pedidos = new ArrayList<>();

    }

    public void adicionarPedido (Pedido pedido){
        this.pedidos.add (pedido);
    }

    public ArrayList<Pedido> recuperarPedidos () {

        return this.pedidos;
    }
}
