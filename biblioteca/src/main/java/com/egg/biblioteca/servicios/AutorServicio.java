package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        // Validar nombre
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre del autor no puede ser nulo o vacío.");
        }

        Autor autor = new Autor();
        autor.setNombre(nombre); // Asumiendo que Autor tiene un método setNombre
        autorRepositorio.save(autor);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return autorRepositorio.findAll();
    }
}
