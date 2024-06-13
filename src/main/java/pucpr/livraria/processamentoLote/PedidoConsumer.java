package pucpr.livraria.processamentoLote;

import pucpr.livraria.entity.Pedido;
import pucpr.livraria.facade.LivrariaFachada;
import pucpr.livraria.processamentoPedido.ProcessamentoPedido;

import java.util.concurrent.BlockingQueue;

public class PedidoConsumer implements Runnable {
    private BlockingQueue<Pedido> queue;
    private LivrariaFachada livrariaFachada;

    public PedidoConsumer(BlockingQueue<Pedido> queue, LivrariaFachada livrariaFachada) {
        this.queue = queue;
        this.livrariaFachada = livrariaFachada;
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
        livrariaFachada.getChainOfResponsibility().statusPedido(ProcessamentoPedido.PAGAMENTO, pedido);
    }
}
