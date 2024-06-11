package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EntregaTransportador extends ProcessamentoPedido{
    public EntregaTransportador() {
        this.level = ProcessamentoPedido.TRANSPORTADOR;
    }

    @Override
    public String processar(Pedido pedido) {
        String mensagem = String.format("Pedido entregue ao transportador. Peso total do pedido: %.2f kg", pedido.getPeso()/1000);
        System.out.println(mensagem);
        if (proximo != null) {
            return mensagem + "<br/>"+ proximo.processar(pedido);
        }
        else{
            return mensagem;
        }
    }
}
