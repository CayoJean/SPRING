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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(String titulo, String descripcion, String idAutor, String idEditorial) throws MiException {
        // Llamar al método de validación
        validar(titulo, descripcion, idAutor, idEditorial);
        
        // 1. Obtener el autor existente por ID
        Optional<Autor> autorOpt = autorRepositorio.findById(idAutor);
        if (autorOpt.isEmpty()) {
            throw new IllegalArgumentException("Autor no encontrado con el ID proporcionado.");
        }
        Autor autor = autorOpt.get();

        // 2. Obtener la editorial existente por ID
        Optional<Editorial> editorialOpt = editorialRepositorio.findById(idEditorial);
        if (editorialOpt.isEmpty()) {
            throw new IllegalArgumentException("Editorial no encontrada con el ID proporcionado.");
        }
        Editorial editorial = editorialOpt.get();

        // 3. Crear una instancia de Libro y setear sus atributos
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setDescripcion(descripcion);
        libro.setAutor(autor); // Asignar el autor al libro
        libro.setEditorial(editorial); // Asignar la editorial al libro
        libro.setAlta(new Date()); // Fecha de alta

        // 4. Persistir el libro en la base de datos
        libroRepositorio.save(libro);
    }

    // Método para modificar un libro existente
    @Transactional
    public void modificarLibro(String id, String titulo, String descripcion, String idAutor, String idEditorial) throws MiException {
        // Llamar al método de validación
        validar(titulo, descripcion, idAutor, idEditorial);
        
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
            // Se establece el nuevo título y descripción, si no son nulos
            libro.setTitulo(titulo);
            libro.setDescripcion(descripcion);

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

            // Se persiste el libro modificado en la base de datos
            libroRepositorio.save(libro);
        } else {
            throw new IllegalArgumentException("Libro no encontrado con el ID proporcionado.");
        }
    }

    // Método de validación
    private void validar(String titulo, String descripcion, String idAutor, String idEditorial) throws MiException {
        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El título del libro no puede ser nulo o estar vacío.");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new MiException("La descripción del libro no puede ser nula o estar vacía.");
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
