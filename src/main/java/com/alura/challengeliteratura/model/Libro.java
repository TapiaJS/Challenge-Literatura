package com.alura.challengeliteratura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    String titulo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "autor_libro",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    List<String> idiomas;
    Double numeroDeDescargas;

    public Libro() {}

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.autores = Autor.listaDeAutores(datosLibros.autor());
        this.idiomas = datosLibros.idiomas();
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        String nombresAutores = autores.stream()
                .map(Autor::getNombre)
                .collect(Collectors.joining("; "));

        return "------ LIBRO ------\n" +
                " Titulo: " + titulo + "\n" +
                " Autores: " + nombresAutores + "\n" +
                " Idiomas: " + idiomas + "\n" +
                " Numero de descargas: " + numeroDeDescargas+ "\n" +
                "-------------------\n";
    }
}
