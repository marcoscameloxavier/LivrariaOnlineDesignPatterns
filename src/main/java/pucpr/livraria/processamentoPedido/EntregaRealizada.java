package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class EntregaRealizada extends ProcessamentoPedido{

    public EntregaRealizada() {
        this.level = ProcessamentoPedido.ENTREGUE;
    }

    @Override
    public void processar(Pedido pedido) {
        // Entrega realizada
        System.out.printf("Entrega do cliente %s realizada com sucesso\n", pedido.getCliente().getNome());
    }
}
