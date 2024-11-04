package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form"; // Asegúrate de que el archivo editorial_form.html exista
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        try {
            editorialServicio.crearEditorial(nombre);
            redirectAttributes.addFlashAttribute("exito", "Editorial registrada exitosamente.");
            return "redirect:/"; // Redirige a la página principal
        } catch (MiException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editorial/registrar"; // Regresar al formulario en caso de error
        }
    }

    @GetMapping("/lista") // Ruta para listar editoriales, ejemplo: localhost:8080/editorial/lista
    public String listar(Model model) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales(); // Obtiene la lista de editoriales
        model.addAttribute("editoriales", editoriales); // Agrega la lista al modelo
        return "editorial_lista"; // Devuelve la vista editorial_lista.html
    }
}


