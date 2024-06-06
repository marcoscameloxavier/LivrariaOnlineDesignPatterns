package pucpr.livraria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pucpr.livraria.entity.Livro;
import pucpr.livraria.facade.LivrariaFachada;

import java.util.List;

@RestController
public class LivrariaController {
    @Autowired
    private LivrariaFachada livrariaFachada;

    @GetMapping("/livros/titulo")
    public ResponseEntity<List<Livro>> getLivrosPorTitulo(String titulo) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorTitulo(titulo);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/autor")
    public ResponseEntity<List<Livro>> getLivrosPorAutor(String autor) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorAutor(autor);
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/livros/genero")
    public ResponseEntity<List<Livro>> getLivrosPorGenero(String genero) {
        List<Livro> livros = livrariaFachada.buscarLivrosPorGenero(genero);
        return ResponseEntity.ok(livros);
    }
}
