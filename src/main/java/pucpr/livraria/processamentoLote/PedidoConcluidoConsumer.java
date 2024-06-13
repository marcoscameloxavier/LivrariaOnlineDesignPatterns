package pucpr.livraria.processamentoLote;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import pucpr.livraria.entity.Pedido;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class PedidoConcluidoConsumer implements Runnable {
    private BlockingQueue<Pedido> queue;
    private CopyOnWriteArrayList<SseEmitter> emitters;

    public PedidoConcluidoConsumer(BlockingQueue<Pedido> queue, CopyOnWriteArrayList<SseEmitter> emitters) {
        this.queue = queue;
        this.emitters = emitters;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Pedido pedido = queue.take();
                String status = pedido.getStatusProcessamento();
                for (SseEmitter emitter : emitters) {
                    try {
                        emitter.send(status);
                        Thread.sleep(1000); // Simula um processamento
                    } catch (Exception e) {
                        emitters.remove(emitter);
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
