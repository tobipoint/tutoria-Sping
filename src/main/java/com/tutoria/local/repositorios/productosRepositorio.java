package com.tutoria.local.repositorios;

import com.tutoria.local.entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface productosRepositorio extends JpaRepository<Productos, String> {

    @Query("SELECT u FROM Productos u WHERE u.tipo = :tipo")
    Productos buscarPorTipo(@Param("tipo") String tipo);
    
}
