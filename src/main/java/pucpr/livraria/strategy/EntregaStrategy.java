package pucpr.livraria.strategy;

import pucpr.livraria.entity.Pedido;

public interface EntregaStrategy {
    double calcularCustoEnvio(Pedido pedido);
}
