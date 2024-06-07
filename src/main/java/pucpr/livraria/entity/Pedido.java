package pucpr.livraria.entity;

import pucpr.livraria.strategy.EntregaStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Pedido {
    private double peso;
    private EntregaStrategy entregaStrategy;

    private Cliente cliente;
    private ArrayList<Livro> listaLivro;

    public Pedido (Cliente cliente, ArrayList<Livro> listaLivro) {
        this.cliente = cliente;
        this.listaLivro = listaLivro;
        this.peso = calcularPeso();
    }

    private double calcularPeso() {
         double peso = 0;
         for (int i = 0; i<listaLivro.size(); i++) {
             peso += listaLivro.get(i).getPesoLivro();
         }
         return peso;
    }

    public double getPeso() {
        return peso;
    }


    public double calcularCustoEnvio() {
        return entregaStrategy.calcularCustoEnvio(this);
    }

    public void setEntregaStrategy(EntregaStrategy entregaStrategy) {
        this.entregaStrategy = entregaStrategy;
    }
}
