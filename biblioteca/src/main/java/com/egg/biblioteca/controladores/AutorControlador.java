package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "autor_form"; // Asegúrate de que el archivo autor_form.html exista en templates
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        try {
            autorServicio.crearAutor(nombre);
            redirectAttributes.addFlashAttribute("exito", "Autor registrado exitosamente.");
            return "redirect:/"; // Redirige a la página principal
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/autor/registrar"; // Regresa al formulario en caso de error
        }
    }

    @GetMapping("/lista") // Ruta para listar autores, ejemplo: localhost:8080/autor/lista
    public String listar(Model model) {
        List<Autor> autores = autorServicio.listarAutores(); // Obtiene la lista de autores
        model.addAttribute("autores", autores); // Agrega la lista al modelo
        return "autor_lista"; // Devuelve la vista autor_lista.html
    }
}
