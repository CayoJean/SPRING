package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException; // Asegúrate de importar MiException
import com.egg.biblioteca.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        // Validar el nombre
        validar(nombre);
        
        // Instanciamos un nuevo objeto Autor y le asignamos el nombre recibido
        Autor autor = new Autor();
        autor.setNombre(nombre);

        // Guardamos el autor en la base de datos
        autorRepositorio.save(autor);
    }

    // ✏️Actividad Edición de datos Método para modificar un autor existente
    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException {     
        // Validar el nombre
        validar(nombre);

        // Busca el autor por ID
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        // Verifica si el autor existe
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            
            // Modifica el nombre del autor
            autor.setNombre(nombre);
            
            // Guarda el autor actualizado en la base de datos
            autorRepositorio.save(autor);
        } else {
            // Opcional: lanzar una excepción si el autor no se encuentra
            throw new IllegalArgumentException("Autor no encontrado con el ID proporcionado.");
        }
    }

    // Método de validación
    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
    }

    // Otros métodos de AutorServicio
}

