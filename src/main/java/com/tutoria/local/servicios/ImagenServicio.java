
package com.tutoria.local.servicios;

import com.tutoria.local.entidades.Imagen;
import com.tutoria.local.excepciones.excepciones;
import com.tutoria.local.repositorios.ImagenRepositorio;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImagenServicio {
    
     @Autowired
    private ImagenRepositorio imagenRepositorio;
     
      @Transactional
    public Imagen guardar(MultipartFile archivo) throws excepciones {

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
