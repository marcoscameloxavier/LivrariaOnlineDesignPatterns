package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EntregaRealizada extends ProcessamentoPedido{

    public EntregaRealizada() {
        this.level = ProcessamentoPedido.ENTREGUE;
    }

    @Override
    public String processar(Pedido pedido) {
        String mensagem = String.format("Entrega do cliente %s realizada com sucesso", pedido.getCliente().getNome());
        System.out.println(mensagem);
        if (proximo != null) {
            return mensagem + "<br/>"+ proximo.processar(pedido);
        }
        else{
            return mensagem;
        }
    }
}
