package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class PagamentoPedido extends ProcessamentoPedido{
    public PagamentoPedido() {
        this.level = ProcessamentoPedido.PAGAMENTO;
    }

    @Override
    public String processar(Pedido pedido) {
        String mensagem = String.format("Pagamento realizado com sucesso!");
        System.out.println(mensagem);
        if (proximo != null) {
            return mensagem + "<br/>"+ proximo.processar(pedido);
        }
        else{
            return mensagem;
        }
    }
}
