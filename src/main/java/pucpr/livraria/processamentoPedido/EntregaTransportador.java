package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EntregaTransportador extends ProcessamentoPedido{
    public EntregaTransportador() {
        this.level = ProcessamentoPedido.TRANSPORTADOR;
    }

    @Override
    public void processar(Pedido pedido) {
        System.out.printf("Pedido entregue ao transportador. Peso total do pedido: %.2f kg \n", pedido.getPeso()/1000);
        if (proximo != null) {
            proximo.processar(pedido);
        }
    }
}
