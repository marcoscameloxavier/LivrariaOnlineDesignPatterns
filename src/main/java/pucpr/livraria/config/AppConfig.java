package pucpr.livraria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pucpr.livraria.entity.Pedido;
import pucpr.livraria.processamentoLote.PedidoConcluidoProducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class AppConfig {

    @Bean
    public BlockingQueue<Pedido> pedidoConcluidoQueue() {
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public PedidoConcluidoProducer pedidoConcluidoProducer(BlockingQueue<Pedido> pedidoConcluidoQueue) {
        return new PedidoConcluidoProducer(pedidoConcluidoQueue);
    }
}
