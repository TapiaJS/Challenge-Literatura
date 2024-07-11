package com.alura.challengeliteratura.principal;

import com.alura.challengeliteratura.colors.Colors;
import com.alura.challengeliteratura.model.Autor;
import com.alura.challengeliteratura.model.Datos;
import com.alura.challengeliteratura.model.DatosLibros;
import com.alura.challengeliteratura.model.Libro;
import com.alura.challengeliteratura.repository.AutorRepository;
import com.alura.challengeliteratura.repository.LibroRepository;
import com.alura.challengeliteratura.service.ConsumoAPI;
import com.alura.challengeliteratura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner m = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> libros;
    private List<Autor> autores;
    private static final String error = "Por favor ingresa una opción válida.";

    public Principal(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
        this.libroRepositorio = libroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void interactuarConElMenu() {
        int opcion;
        libros = libroRepositorio.findAll();
        do {
            var menu = """
                    -------------------
                    Elija la opción através de su número:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    7 - Salir
                    """;
            opcion = getInt(menu, error, 1, 7);
            m.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    if (exiteLibrosEnLaBase()){
                        mostrarLibrosListados();
                    }
                    break;
                case 3:
                    if (exiteLibrosEnLaBase()){
                        mostrarLibrosListadosPorAutor();
                    }
                    break;
                case 4:
                    if (exiteLibrosEnLaBase()){
                        mostrarLibrosListadosPorAutoresPorAño();
                    }
                    break;
                case 5:
                    if (exiteLibrosEnLaBase()){
                        mostrarLibrosListadosPorIdioma();
                    }
                    break;
                case 7:
                    Colors.println("¡Hasta luego!", Colors.BLUE);
                    break;
                default:
                    Colors.println("Opción no válida. Inténtelo de nuevo.", Colors.RED);
            }
        } while (opcion != 7);
    }

    private void buscarLibroPorTitulo() {
        DatosLibros datos = getDatosLibros();
        if (datos != null) {
            // Verificar si el libro ya existe en la base de datos
            Optional<Libro> libroExistente = libroRepositorio.findByTituloContainsIgnoreCase(datos.titulo());

            if (libroExistente.isPresent()) {
                Colors.println("El libro ya está registrado:", Colors.HIGH_INTENSITY + Colors.ITALICS);
                Colors.println(libroExistente.get(), Colors.YELLOW);
            } else {
                // Crear un nuevo libro y lo guarda
                Libro libro = new Libro(datos);
                libroRepositorio.save(libro);
                libros = libroRepositorio.findAll();
                Colors.println("Libro guardado exitosamente:", Colors.GREEN);
                Colors.println(libro, Colors.YELLOW);
            }
        }
    }


    private void mostrarLibrosListados() {
        libros = libroRepositorio.findAll();
        libros.stream().forEach(l -> Colors.println(l, Colors.YELLOW));
    }

    private void mostrarLibrosListadosPorAutor() {
        libros.forEach(libro -> {
            List<Autor> autores = libro.getAutores();
            autores.forEach(autor -> {
                Colors.println(autor, Colors.YELLOW);
            });
        });
    }

    private void mostrarLibrosListadosPorAutoresPorAño() {
        int año = getInt("Ingresa el año para saber que autor(es) vivian", error, -1000, 2024);

        autores = autorRepositorio.findAll();
        List<Autor> autoresFiltrados = autores.stream()
                .filter(a -> a.getFechaDeNacimiento() != null && a.getFechaDeNacimiento() < año &&
                        a.getFechaDeFallecimiento() != null && a.getFechaDeFallecimiento() > año)
                .collect(Collectors.toList());

        if (!autoresFiltrados.isEmpty()) {
            autoresFiltrados.forEach(autor -> Colors.println(autor, Colors.YELLOW));
        } else {
            Colors.println("No se encontraron autores fallecidos después del año " + año, Colors.RED);
        }
    }

    private void mostrarLibrosListadosPorIdioma() {
        String mensaje = """
                Ingresa el idioma para buscar los libros:
                es- español
                en- ingles
                fr- frances
                pt- portugués
                """;
        Colors.println(mensaje, Colors.HIGH_INTENSITY);
        String idioma = m.nextLine().toLowerCase();

        if (!List.of("es", "en", "fr", "pt").contains(idioma)) {
            Colors.println("Idioma no válido. Inténtelo de nuevo.", Colors.RED);
            return;
        }

        List<Libro> librosFiltrados = libros.stream()
                .filter(libro -> libro.getIdiomas().contains(idioma))
                .collect(Collectors.toList());

        if (!librosFiltrados.isEmpty()) {
            librosFiltrados.forEach(libro -> Colors.println(libro, Colors.YELLOW));
        } else {
            Colors.println("No se encontraron libros en el idioma " + idioma, Colors.RED);
        }
    }

    private boolean exiteLibrosEnLaBase(){
        if (libros.isEmpty()){
            Colors.println("No existen libros en la base de datos. Busca algún libro para agregarlo.", Colors.RED);
            return false;
        } else {
          return true;
        }
    }

    private DatosLibros getDatosLibros() {
        Colors.println("Ingrese el nombre del libro que desea buscar", Colors.HIGH_INTENSITY);
        var tituloLibro = m.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> librosBusqueda = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (librosBusqueda.isPresent()) {
            return librosBusqueda.get();
        } else {
            System.out.println("Libro no encontrado");
            return null;
        }
    }

    private int getInt(String mensaje, String error, int min, int max) {
        int valor;

        while (true) {
            Colors.println(mensaje, Colors.HIGH_INTENSITY);
            if (m.hasNextInt()) {
                valor = m.nextInt();

                if (valor < min || max < valor) {
                    Colors.println(error, Colors.RED + Colors.HIGH_INTENSITY);
                } else {
                    return valor;
                }
            } else {
                m.next();
                Colors.println(error, Colors.RED + Colors.HIGH_INTENSITY);
            }
        }
    }
}
