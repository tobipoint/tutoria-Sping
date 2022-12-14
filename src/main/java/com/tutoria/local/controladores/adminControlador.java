package com.tutoria.local.controladores;

import com.tutoria.local.entidades.Productos;
import com.tutoria.local.servicios.ProductosServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class adminControlador {

    @Autowired
    private ProductosServicio ProductosServicio;
    
    @GetMapping("/dashboard")
    public String panel(ModelMap modelo) {
        List<Productos> productos = ProductosServicio.listar();
        modelo.put("productos", productos);
        return "inicio";
    }

}
