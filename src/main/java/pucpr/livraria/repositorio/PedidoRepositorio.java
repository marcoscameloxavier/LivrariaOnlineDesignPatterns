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

    public Pedido getPedidoPorId(int idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == idPedido) {
                return pedido;
            }
        }
        return null;
    }
}
