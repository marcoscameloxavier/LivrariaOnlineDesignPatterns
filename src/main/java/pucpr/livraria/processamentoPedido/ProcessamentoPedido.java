package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public abstract class ProcessamentoPedido {
    protected ProcessamentoPedido proximo;

    public void setProximo(ProcessamentoPedido proximo) {
        this.proximo = proximo;
    }

    public abstract void processar(Pedido pedido);
}
