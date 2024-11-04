package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import com.egg.biblioteca.excepciones.MiException; // Importar la clase de excepción
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, String descripcion, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        // Llamar al método de validación
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        // 1 Obtener el autor existente por ID
        Optional<Autor> autorOpt = autorRepositorio.findById(idAutor);
        if (autorOpt.isEmpty()) {
            throw new IllegalArgumentException("Autor no encontrado con el ID proporcionado.");
        }
        Autor autor = autorOpt.get();

        // 2 Obtener la editorial existente por ID
        Optional<Editorial> editorialOpt = editorialRepositorio.findById(idEditorial);
        if (editorialOpt.isEmpty()) {
            throw new IllegalArgumentException("Editorial no encontrada con el ID proporcionado.");
        }
        Editorial editorial = editorialOpt.get();

        // 3 Crear una instancia de Libro y setear sus atributos
        Libro libro = new Libro();
        libro.setIsbn(isbn); // Cambiado a Long
        libro.setTitulo(titulo);
        libro.setDescripcion(descripcion);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor); // Asignar el autor al libro
        libro.setEditorial(editorial); // Asignar la editorial al libro
        libro.setAlta(new Date()); // Fecha de alta

        // 4 Persistir el libro en la base de datos
        libroRepositorio.save(libro);
    }

    // Método para modificar un libro existente
    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, String descripcion, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        // Llamar al método de validación
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        // Convertir el ID a Long
        Long libroId;
        try {
            libroId = Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID del libro debe ser un número válido.");
        }

        Optional<Libro> libroOpt = libroRepositorio.findById(libroId);
        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            // Se establece el nuevo título, descripción y ejemplares, si no son nulos
            libro.setTitulo(titulo);
            libro.setDescripcion(descripcion);
            libro.setEjemplares(ejemplares);
            libro.setIsbn(isbn); // Cambiado a Long

            // Se actualizan el autor y la editorial solo si se proporcionan
            if (idAutor != null) {
                Optional<Autor> autorOpt = autorRepositorio.findById(idAutor);
                if (autorOpt.isPresent()) {
                    libro.setAutor(autorOpt.get());
                } else {
                    throw new IllegalArgumentException("Autor no encontrado con el ID proporcionado.");
                }
            }
            if (idEditorial != null) {
                Optional<Editorial> editorialOpt = editorialRepositorio.findById(idEditorial);
                if (editorialOpt.isPresent()) {
                    libro.setEditorial(editorialOpt.get());
                } else {
                    throw new IllegalArgumentException("Editorial no encontrada con el ID proporcionado.");
                }
            }

            // Persistir el libro modificado en la base de datos
            libroRepositorio.save(libro);
        } else {
            throw new IllegalArgumentException("Libro no encontrado con el ID proporcionado.");
        }
    }

    // Método de validación
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        if (isbn == null) {
            throw new MiException("El ISBN del libro no puede ser nulo.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El título del libro no puede ser nulo o estar vacío.");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new MiException("Debe especificar al menos un ejemplar.");
        }
        if (idAutor == null || idAutor.isEmpty()) {
            throw new MiException("El ID del autor no puede ser nulo o estar vacío.");
        }
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new MiException("El ID de la editorial no puede ser nulo o estar vacío.");
        }
    }

    // Método para listar libros
    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        return libroRepositorio.findAll();
    }

    // Método para listar autores
    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }

    // Método para listar editoriales
    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        return editorialRepositorio.findAll();
    }
}
*/
import java.util.UUID;


@Service
public class LibroServicio {


    private void validar(Long isbn, String titulo, Integer ejemplares, Date alta, Autor autor, Editorial editorial) throws MiException {
        if (isbn == null) {
            throw new MiException("El ISBN no puede ser nulo");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new MiException("El título no puede ser nulo o estar vacío");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new MiException("El número de ejemplares debe ser mayor a cero");
        }
        if (alta == null) {
            throw new MiException("La fecha de alta no puede ser nula");
        }
        if (autor == null) {
            throw new MiException("El autor no puede ser nulo");
        }
        if (editorial == null) {
            throw new MiException("La editorial no puede ser nula");
        }
    }
       
@Autowired
private LibroRepositorio libroRepositorio;
@Autowired
private AutorRepositorio autorRepositorio;
@Autowired
private EditorialRepositorio editorialRepositorio;


public void crearLibro(Long isbn, String titulo, Integer ejemplares, UUID autorID, UUID editorialID) throws MiException {
    Date alta = new Date();
    
    Autor autor = autorRepositorio.findById(autorID)
            .orElseThrow(() -> new MiException("No se encontró el autor con el ID proporcionado"));
    Editorial editorial = editorialRepositorio.findById(editorialID)
            .orElseThrow(() -> new MiException("No se encontró la editorial con el ID proporcionado"));

    validar(isbn, titulo, ejemplares, alta, autor, editorial);

    Libro libro = new Libro();
    libro.setIsbn(isbn);
    libro.setTitulo(titulo);
    libro.setEjemplares(ejemplares);
    libro.setAlta(alta);
    libro.setAutor(autor);
    libro.setEditorial(editorial);

    libroRepositorio.save(libro); 
}

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList<>(); // Ahora está parametrizado correctamente

        libros = libroRepositorio.findAll();
        return libros;
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, Date alta, UUID autorID, UUID editorialID) throws MiException {
    
    Autor autor = autorRepositorio.findById(autorID)
            .orElseThrow(() -> new MiException("No se encontró el autor con el ID proporcionado"));
    Editorial editorial = editorialRepositorio.findById(editorialID)
            .orElseThrow(() -> new MiException("No se encontró la editorial con el ID proporcionado"));

        validar(isbn, titulo, ejemplares, alta, autor, editorial);

        Libro libro = libroRepositorio.findById(isbn)
                .orElseThrow(() -> new MiException("No se encontró el libro con el ISBN proporcionado"));
        
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(alta);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }

}