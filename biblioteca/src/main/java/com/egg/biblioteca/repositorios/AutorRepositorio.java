package com.egg.biblioteca.repositorios;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.egg.biblioteca.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
    // Aquí puedes añadir métodos personalizados según sea necesario
}