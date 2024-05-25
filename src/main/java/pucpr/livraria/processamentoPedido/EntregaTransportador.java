package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EntregaTransportador extends ProcessamentoPedido{
    @Override
    public void processar(Pedido pedido) {
        // Entrega pelo transportador
        if (proximo != null) {
            proximo.processar(pedido);
        }
    }
}
