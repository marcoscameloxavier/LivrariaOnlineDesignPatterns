package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class SaidaEntrega extends ProcessamentoPedido{
    public SaidaEntrega() {
        this.level = ProcessamentoPedido.ENTREGA;
    }

    @Override
    public void processar(Pedido pedido) {
        System.out.printf("Seu pedido saiu para entrega para o endereço %s! \n", pedido.getCliente().getEndereco());
        if (proximo != null) {
            proximo.processar(pedido);
        }
    }
}
