package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class SaidaEntrega extends ProcessamentoPedido{
    public SaidaEntrega() {
        this.level = ProcessamentoPedido.ENTREGA;
    }

    @Override
    public void processar(Pedido pedido) {
        System.out.println("Seu pedido saiu para entrega!");
        if (proximo != null) {
            proximo.processar(pedido);
        }
    }
}
