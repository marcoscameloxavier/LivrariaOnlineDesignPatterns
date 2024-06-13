package pucpr.livraria.processamentoLote;

import pucpr.livraria.entity.Pedido;

import java.util.concurrent.BlockingQueue;

public class PedidoConcluidoProducer {
    private BlockingQueue<Pedido> queue;

    public PedidoConcluidoProducer(BlockingQueue<Pedido> queue) {
        this.queue = queue;
    }

    public void addPedidoConcluido(Pedido pedidoConcluido) throws InterruptedException {
        queue.put(pedidoConcluido);
    }

    public BlockingQueue<Pedido> getQueue() {
        return queue;
    }
}
