package com.tutoria.local.controladores;

import com.tutoria.local.entidades.Productos;
import com.tutoria.local.excepciones.excepciones;
import com.tutoria.local.servicios.ProductosServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    private ProductosServicio productoServicio;

    @GetMapping("/crear")
    public String crear() {

        return "crearProducto";
    }

    @PostMapping("/cargar")
    public String cargar(@RequestParam MultipartFile archivo, @RequestParam String tipo,
            @RequestParam int precio, @RequestParam String tamaño, @RequestParam String material, ModelMap modelo) throws excepciones {
        try {
            productoServicio.añadirProducto(archivo, tipo, precio, tamaño, material);
            modelo.put("exito", "el producto se cargo con exito");
            return "inicio";
        } catch (excepciones e) {
            modelo.put("error", "el producto no se ha cargado");
            return "crearProducto";
        }
    }

    @GetMapping("/editar/{id}") //localhost8080/Noticia/editar
    public String NoticiaEditar(@PathVariable String id, ModelMap modelo) throws excepciones {
        Productos producto = productoServicio.getOne(id);
        modelo.addAttribute("producto", producto);

        return "editarProducto";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam MultipartFile archivo, @RequestParam String tipo,
            @RequestParam int precio, @RequestParam String tamaño, @RequestParam String material, ModelMap modelo) throws excepciones {
        try {
            Productos noticias = productoServicio.getOne(id);
            modelo.addAttribute("noticias", noticias);
            productoServicio.acutalizar(tipo, precio, tamaño, precio, tipo, material);
            return "redirect:/inicio";
        } catch (excepciones e) {
            List<Productos> producto = productoServicio.listar();
            modelo.addAttribute("producto", producto);
            return "editarProducto";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String productoEliminar(@PathVariable String id, ModelMap modelo) throws excepciones {
        modelo.put("noticias", productoServicio.getOne(id));

        return "eliminarNoticia";
    }

    @PostMapping("/darDeBaja/{id}")
    public String Eliminar(@PathVariable String id, ModelMap modelo) throws excepciones {
        System.out.println("eliminando...");
        productoServicio.getOne(id);
        try {
            productoServicio.eliminar(id);
            modelo.put("exito", "la noticia se elimino correctamente");
            return "redirect:/inicio";
        } catch (Exception e) {
            modelo.put("error", "el libro no se ha eliminado");
            return "eliminarProducto";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE')")
    @GetMapping("/comprar")
    public String comprar(ModelMap modelo, @PathVariable String id ) {
        Productos producto = productoServicio.getOne(id);
        
        return "listaCompra";
    }

}
