package pucpr.livraria.processamentoLote;

import pucpr.livraria.entity.Pedido;

import java.util.concurrent.BlockingQueue;

public class PedidoProducer {
    private BlockingQueue<Pedido> queue;

    public PedidoProducer(BlockingQueue<Pedido> queue) {
        this.queue = queue;
    }

    public void addOrder(Pedido pedido) throws InterruptedException {
        queue.put(pedido);
        System.out.printf("Pedido n. %d adicionado à fila \n", pedido.getId());
    }
}
