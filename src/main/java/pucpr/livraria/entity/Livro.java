package pucpr.livraria.entity;

import java.time.LocalDate;

public class Livro {
    //dados do livro obtido da API
    private String titulo;
    private String autor;
    private String genero;
    private String sinopse;
    private LocalDate dataPublicacao;
    private String editora;
    private double preco;

    public Livro(String titulo){
        this.titulo = titulo;
    }

    public Livro(String titulo, String autor, String genero, String sinopse, LocalDate dataPublicacao, String editora, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.sinopse = sinopse;
        this.dataPublicacao = dataPublicacao;
        this.editora = editora;
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
