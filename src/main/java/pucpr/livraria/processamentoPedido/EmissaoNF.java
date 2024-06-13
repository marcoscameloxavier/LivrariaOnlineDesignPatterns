package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EmissaoNF extends ProcessamentoPedido{

    public EmissaoNF() {
        this.level = ProcessamentoPedido.NF;
    }

    @Override
    public String processar(Pedido pedido) {
        // Emissão de NF

        String mensagem = String.format("PEDIDO n. %d: Nota fiscal emitida no valor total de R$%.2f, com custo de envio de R$%.2f", pedido.getId(), pedido.getValorTotal(), pedido.getValorFrete());
        System.out.println(mensagem);
            if (proximo != null) {
                return mensagem + "<br/>"+ proximo.processar(pedido);
            }
            else{
                return mensagem;
            }
    }
}
