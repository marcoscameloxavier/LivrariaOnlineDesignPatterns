package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class PagamentoPedido extends ProcessamentoPedido{
    public PagamentoPedido() {
        this.level = ProcessamentoPedido.PAGAMENTO;
    }

    @Override
    public void processar(Pedido pedido) {
        System.out.println("Pagamento realizado com sucesso!");
        if (proximo != null) {
            proximo.processar(pedido);
        }
    }
}
