package pucpr.livraria.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pucpr.livraria.strategy.EntregaStrategy;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Pedido {
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int id;
    private double peso;
    private String statusProcessamento;
    private EntregaStrategy entregaStrategy;

    @JsonBackReference
    private Cliente cliente;
    private ArrayList<Livro> listaLivro;

    public Pedido(Cliente cliente, ArrayList<Livro> listaLivro) {
        this.id = count.incrementAndGet();
        this.cliente = cliente;
        this.listaLivro = listaLivro;
        this.peso = calcularPeso();
        this.statusProcessamento = "";
    }

    private double calcularPeso() {
        double peso = 0;
        for (int i = 0; i < listaLivro.size(); i++) {
            peso += listaLivro.get(i).getPesoLivro();
        }
        return peso;
    }

    public int getId() {
        return id;
    }

    public double getPeso() {
        return peso;
    }

    private double calcularCustoEnvio() {
        return entregaStrategy.calcularCustoEnvio(this);
    }

    public void setEntregaStrategy(EntregaStrategy entregaStrategy) {
        this.entregaStrategy = entregaStrategy;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Livro> getListaLivro() {
        return listaLivro;
    }

    public double getValorTotal() {
        double valorTotal = 0;
        for (int i = 0; i < listaLivro.size(); i++) {
            valorTotal += listaLivro.get(i).getPreco();
        }
        valorTotal += calcularCustoEnvio();
        return valorTotal;
    }

    public double getValorFrete() {
        return this.calcularCustoEnvio();
    }

    public EntregaStrategy getEntregaStrategy() {
        return entregaStrategy;
    }

    public String getStatusProcessamento() {
        return statusProcessamento;
    }

    public void atualizaStatusProcessamento(String status) {
        this.statusProcessamento += "<br/>"+ status;
    }
}
