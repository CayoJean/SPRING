package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.excepciones.MiException; // Importar la clase de excepción
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        // Llamar al método de validación
        validar(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws MiException {
        // Llamar al método de validación
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre); // Seteamos el nuevo nombre
            editorialRepositorio.save(editorial); // Persistimos la editorial
        } else {
            throw new IllegalArgumentException("Editorial no encontrada con el ID proporcionado.");
        }
    }

    // Método de validación
    private void validar(String nombre) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre de la editorial no puede ser nulo o estar vacío.");
        }
    }
}