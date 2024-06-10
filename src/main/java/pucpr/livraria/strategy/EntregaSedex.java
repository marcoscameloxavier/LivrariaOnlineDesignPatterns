package pucpr.livraria.strategy;

import pucpr.livraria.entity.Pedido;

public class EntregaSedex implements EntregaStrategy{
    @Override
    public double calcularCustoEnvio(Pedido pedido) {
        return pedido.getPeso() * 0.08;
    }
}
