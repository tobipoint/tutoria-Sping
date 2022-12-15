package com.tutoria.local.controladores;

import com.tutoria.local.entidades.Productos;
import com.tutoria.local.entidades.Usuario;
import com.tutoria.local.servicios.ProductosServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private ProductosServicio ProductosServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Productos> productos = ProductosServicio.listar();
        modelo.put("productos", productos);
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contraseña incorrecto");
        }
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_DUEÑO')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        List<Productos> productos = ProductosServicio.listar();
        modelo.put("productos", productos);

        if (logueado.getRol().toString().equals("DUEÑO")) {
            return "redirect:/admin/dashboard";
        }

        return "inicio";
    }

}
