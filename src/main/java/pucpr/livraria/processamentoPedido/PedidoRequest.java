package pucpr.livraria.processamentoPedido;

import pucpr.livraria.entity.Livro;
import pucpr.livraria.strategy.EntregaStrategy;

import java.util.List;

public class PedidoRequest {
    private String cpf;
    private List<Livro> livros;
    private EntregaStrategy entrega;

    // Getters e setters

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public EntregaStrategy getEntrega() {
        return entrega;
    }

    public void setEntrega(EntregaStrategy entrega) {
        this.entrega = entrega;
    }
}
