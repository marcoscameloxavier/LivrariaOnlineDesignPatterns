package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EmissaoNF extends ProcessamentoPedido{

    public EmissaoNF() {
        this.level = ProcessamentoPedido.NF;
    }

    @Override
    public void processar(Pedido pedido) {
        // Emissão de NF

        System.out.printf("Nota fiscal emitida no valor total de R$%.2f, com custo de envio de R$%.2f \n", pedido.getValorTotal(), pedido.getValorFrete());

            if (proximo != null) {
                proximo.processar(pedido);
            }
    }
}
