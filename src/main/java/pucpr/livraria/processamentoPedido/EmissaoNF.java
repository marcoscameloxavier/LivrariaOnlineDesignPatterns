package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EmissaoNF extends ProcessamentoPedido{

    public EmissaoNF() {
        this.level = ProcessamentoPedido.NF;
    }

    @Override
    public void processar(Pedido pedido) {
        // Emissão de NF

        System.out.println("Nota fiscal emitida");

            if (proximo != null) {
                proximo.processar(pedido);
            }
    }
}
