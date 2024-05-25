package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EmissaoNF extends ProcessamentoPedido{
    @Override
    public void processar(Pedido pedido) {
        // Emissão de NF
            if (proximo != null) {
                proximo.processar(pedido);
            }
    }
}
