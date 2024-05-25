package pucpr.livraria.strategy;

import pucpr.livraria.entity.Pedido;

public class EntregaRapida implements EntregaStrategy{
    @Override
    public double calcularCustoEnvio(Pedido pedido) {
        return pedido.getPeso() * 0.05;
    }
}
