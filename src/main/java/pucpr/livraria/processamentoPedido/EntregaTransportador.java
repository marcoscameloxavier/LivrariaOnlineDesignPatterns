package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EntregaTransportador extends ProcessamentoPedido{
    public EntregaTransportador() {
        this.level = ProcessamentoPedido.TRANSPORTADOR;
    }

    @Override
    public void processar(Pedido pedido) {
        System.out.println("Pedido entregue ao transportador");
        // Entrega pelo transportador
        if (proximo != null) {
            proximo.processar(pedido);
        }
    }
}
