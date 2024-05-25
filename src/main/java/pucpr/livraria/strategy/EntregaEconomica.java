package pucpr.livraria.strategy;

import pucpr.livraria.entity.Pedido;

public class EntregaEconomica implements EntregaStrategy{
    @Override
    public double calcularCustoEnvio(Pedido pedido) {
        return pedido.getPeso() * 1.5;
    }
}
