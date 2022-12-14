package com.tutoria.local.controladores;

import com.tutoria.local.entidades.Usuario;
import com.tutoria.local.excepciones.excepciones;
import com.tutoria.local.servicios.usuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private usuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")
    public String registrar() {

        return "userForm";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2,
            ModelMap modelo) {
        try {
            
            usuarioServicio.crearUsuario(nombre, email, password, password2);
           
            modelo.put("exito", "usuario agregado");
            return "login";
        } catch (excepciones e) {
            modelo.put("error", e.getMessage());
            return "userForm";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listar();
        modelo.addAttribute("usuarios", usuarios);

        return "listarUsuarios";
    }
    
    
    
}
