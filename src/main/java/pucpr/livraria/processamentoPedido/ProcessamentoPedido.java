package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public abstract class ProcessamentoPedido {
    public static int PAGAMENTO = 1;
    public static int NF = 2;
    public static int TRANSPORTADOR = 3;
    public static int ENTREGA = 4;
    public static int ENTREGUE = 5;
    protected int level;

    protected ProcessamentoPedido proximo;

    public void setProximo(ProcessamentoPedido proximo) {
        this.proximo = proximo;
    }

    public String statusPedido(int level, Pedido pedido) {
        if (this.level <= level) {
            return processar(pedido);
        }
        if (proximo != null) {
            return proximo.statusPedido(level, pedido);
        }
        else {
            return "Pedido não processado";
        }
    }

    public abstract String processar(Pedido pedido);
}
