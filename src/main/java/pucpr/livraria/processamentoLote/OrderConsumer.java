package pucpr.livraria.processamentoLote;

import pucpr.livraria.entity.Pedido;
import pucpr.livraria.facade.LivrariaFachada;
import pucpr.livraria.processamentoPedido.ProcessamentoPedido;

import java.util.concurrent.BlockingQueue;

public class OrderConsumer implements Runnable {
    private BlockingQueue<Pedido> queue;

    public OrderConsumer(BlockingQueue<Pedido> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pedido pedido = queue.take();
                System.out.printf("\nPedido n. %d retirado da fila para início do processamento\n", pedido.getId());
                processOrder(pedido);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void processOrder(Pedido pedido) {
        LivrariaFachada.getChainOfResponsibility().statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);
    }
}
