package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;
import pucpr.livraria.processamentoLote.PedidoConcluidoProducer;

public class EntregaRealizada extends ProcessamentoPedido {
    private final PedidoConcluidoProducer pedidoConcluidoProducer;

    public EntregaRealizada(PedidoConcluidoProducer pedidoConcluidoProducer) {
        this.pedidoConcluidoProducer = pedidoConcluidoProducer;
        this.level = ProcessamentoPedido.ENTREGUE;
    }

    @Override
    public String processar(Pedido pedido) {
        String mensagem = String.format("PEDIDO n. %d: Entrega do cliente %s realizada com sucesso", pedido.getId(), pedido.getCliente().getNome());
        pedido.atualizaStatusProcessamento(mensagem);
        System.out.println(mensagem);
        if (proximo != null) {
            return mensagem + "<br/>" + proximo.processar(pedido);
        } else {
            try {
                pedidoConcluidoProducer.addPedidoConcluido(pedido);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
            return mensagem;
        }
    }
}
