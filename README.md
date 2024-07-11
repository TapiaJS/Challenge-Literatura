# Catálogo de Libros Interactivo

Bienvenido al **Chalenge Literatura**, este proyecto Java te permite explorar y gestionar un catálogo de libros interactivo a través de la consola. Aprende a interactuar con una API de libros, manipular datos JSON, almacenar información en una base de datos PostgreSQL y ofrecer una experiencia de usuario completa para la búsqueda y visualización de libros y autores.

## Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Archivos Clave](#archivos-clave)

## Descripción del Proyecto

Este proyecto consiste en un catálogo de libros interactivo desarrollado en Java. Permite a los usuarios realizar diversas acciones a través de la consola:

- Consultar una API específica de libros para obtener datos actualizados.
- Almacenar y gestionar la información de libros y autores en una base de datos PostgreSQL.
- Proporcionar opciones interactivas para buscar libros, ver detalles de libros específicos y explorar información sobre los autores.

## Requisitos

Para ejecutar este proyecto, asegúrate de tener instalado lo siguiente:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) 11 o superior.
- [PostgreSQL](https://www.postgresql.org/download/) instalado y configurado localmente o acceso a una instancia remota.
- Conexión a internet para acceder a la API de libros.

## Instalación

Sigue estos pasos para configurar y ejecutar LiterAlura en tu entorno local:

1. **Clonar el Repositorio**:
    ```bash
    git clone https://github.com/TapiaJS/Challenge-Literatura.git
    ```
   
2. **Navegar al Directorio del Proyecto**:
    ```bash
    cd Challenge-Literatura
    ```

3. **Configurar la Base de Datos**:
    - Configura las credenciales de tu base de datos PostgreSQL en el archivo `application.properties`.


## Uso

Una vez que la aplicación esté en ejecución, sigue las instrucciones en la consola para explorar y gestionar el catálogo de libros:

1. **buscar libro por titulo**: Permite buscar libros por título al consumir la API de libros.
2. **listar libros registrados**: Muestra los libros que se han buscado y guardado en la base de datos.
3. **listar autores registrados**: Muestra los autores que se han guardado al buscar libros en la base de datos.
4. **listar autores vivos en un determinado año**: Muestra los autores que continuaban con vida en el año ingresado.
5. **listar libros por idioma**: El usuario debe elegir entre los 4 idiomas proporcionados, para mostrar los libros disponibles en ese idioma desde la basse de datos. 
7. **Salir**: Termina la ejeción del programa.

### Archivos Clave:

- **ChallengeliteraturaApplication.java**: Punto de entrada principal de la aplicación.
- **Autor.java**: Clase que representa a un autor y sus obras.
- **Libro.java**: Clase que modela un libro con sus atributos y relaciones.
- **AutorRepository.java** y **LibroRepository.java**: Gestión de entidades y consultas a la base de datos.
- **Datos.java**, **DatosLibros.java** y **DatosAutor.java**: Utilidades para consumir y procesar datos de la API de libros.
