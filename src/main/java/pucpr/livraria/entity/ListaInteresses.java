package pucpr.livraria.entity;

public class ListaInteresses {
    private String genero;
    private String autor;

    public ListaInteresses(String genero, String autor) {
        this.genero = genero;
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public String getAutor() {
        return autor;
    }
}
