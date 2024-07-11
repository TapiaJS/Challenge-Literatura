package com.alura.challengeliteratura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    String nombre;
    int fechaDeNacimiento;
    int fechaDeFallecimiento;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = OptionalInt.of(Integer.valueOf(datosAutor.fechaDeNacimiento())).orElse(0);
        this.fechaDeFallecimiento = OptionalInt.of(Integer.valueOf(datosAutor.fechaDeFallecimiento())).orElse(0);
    }

    public static List<Autor> listaDeAutores(List<DatosAutor> datosAutor) {
        return datosAutor.stream()
                .map(a -> new Autor(a))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(Integer fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        String tituloLibros = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(", "));
        return "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaDeNacimiento + "\n" +
                "Fecha de fallecimiento: " + fechaDeFallecimiento + "\n" +
                "Libros: " + tituloLibros + "\n";
    }
}