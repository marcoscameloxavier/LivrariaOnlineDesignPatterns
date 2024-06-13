package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Pedido;

public class SaidaEntrega extends ProcessamentoPedido{
    public SaidaEntrega() {
        this.level = ProcessamentoPedido.ENTREGA;
    }

    @Override
    public String processar(Pedido pedido) {
        String mensagem = String.format("PEDIDO n. %d: Seu pedido saiu para entrega para o endereço %s!",pedido.getId() ,pedido.getCliente().getEndereco());
        System.out.println(mensagem);
        if (proximo != null) {
            return mensagem + "<br/>"+ proximo.processar(pedido);
        }
        else{
            return mensagem;
        }
    }
}
