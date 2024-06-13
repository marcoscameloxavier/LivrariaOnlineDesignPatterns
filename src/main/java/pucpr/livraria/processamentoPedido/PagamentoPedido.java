package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class PagamentoPedido extends ProcessamentoPedido{
    public PagamentoPedido() {
        this.level = ProcessamentoPedido.PAGAMENTO;
    }

    @Override
    public String processar(Pedido pedido) {
        String mensagem = String.format("PEDIDO n. %d: Pagamento realizado com sucesso no valor de R$%.2f!",pedido.getId() ,pedido.getValorTotal());
        pedido.atualizaStatusProcessamento(mensagem);
        System.out.println(mensagem);
        if (proximo != null) {
            return mensagem + "<br/>"+ proximo.processar(pedido);
        }
        else{
            return mensagem;
        }
    }
}
