package com.tutoria.local.controladores;

import com.tutoria.local.entidades.Productos;
import com.tutoria.local.servicios.ProductosServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    private ProductosServicio productosServicio;
    
    @GetMapping("/producto/{id}")
     public ResponseEntity<byte[]> noticiaUsuario(@PathVariable String id) {
        Productos producto = productosServicio.getOne(id);
        byte[] imagen = producto.getImagen().getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
}
