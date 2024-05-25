package pucpr.livraria.entity;

import pucpr.livraria.strategy.EntregaStrategy;

public class Pedido {
    private double peso;
    private EntregaStrategy entregaStrategy;

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double calcularCustoEnvio() {
        return entregaStrategy.calcularCustoEnvio(this);
    }

    public void setEntregaStrategy(EntregaStrategy entregaStrategy) {
        this.entregaStrategy = entregaStrategy;
    }
}
