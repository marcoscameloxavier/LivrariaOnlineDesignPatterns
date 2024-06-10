package pucpr.livraria.entity;

import java.time.LocalDate;

public class Livro {
    //dados do livro obtido da API
    private String titulo;
    private String autor;
    private String genero;
    private String sinopse;
    private String editora;
    private double preco;
    private int numPaginas;
    private String dataPublicacao;
    private String previewLink;
    private String capa;

    public Livro(){
    }

    public Livro(String titulo){
        this.titulo = titulo;
    }

    public Livro(String titulo, String autor, String genero, String sinopse, String editora, int numPaginas, double preco) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.sinopse = sinopse;
        this.editora = editora;
        this.numPaginas = numPaginas;
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

    public double getPesoLivro() {
        return this.numPaginas * 0.4;
    }

    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public int getNumPaginas() {
        return numPaginas;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }
}
