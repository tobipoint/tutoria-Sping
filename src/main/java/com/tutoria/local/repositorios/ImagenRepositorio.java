package com.tutoria.local.repositorios;

import com.tutoria.local.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
    
}
