package com.tutoria.local.servicios;

import com.tutoria.local.entidades.Imagen;
import com.tutoria.local.entidades.Productos;
import com.tutoria.local.excepciones.excepciones;
import com.tutoria.local.repositorios.productosRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductosServicio {

    @Autowired
    private productosRepositorio productosRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void añadirProducto(MultipartFile archivo, String tipo, String marca, int precio, String tamano, String material) throws excepciones {
        validar(tipo, precio, tamano, material);
        Productos producto = new Productos();

        try {
            producto.setMaterial(material);
            producto.setPrecio(precio);
            producto.setTamano(tamano);
            producto.setTipo(tipo);
            producto.setMarca(marca);
            Imagen imagen = imagenServicio.guardar(archivo);
            producto.setImagen(imagen);

            productosRepositorio.save(producto);
        } catch (excepciones e) {
            System.err.println("error");
        }

    }

    public void validar(String tipo, int precio, String tamano, String material) throws excepciones {
        if (tipo == null) {
            throw new excepciones("tipo invalido");
        }

        if (material == null) {
            throw new excepciones("material invalido");
        }
        if (tamano == null) {
            throw new excepciones(" tamaño invalido");
        }
    }

    @Transactional
    public void acutalizar(String tipo, int precio, String tamano, int talle, String color, String material) throws excepciones {
        validar(tipo, precio, tipo, material);

        Productos producto = new Productos();
        producto.setMaterial(material);
        producto.setPrecio(precio);
        producto.setTamano(tamano);
        producto.setTipo(tipo);
        productosRepositorio.save(producto);

    }

    public List<Productos> listar() {
        List<Productos> lista = productosRepositorio.findAll();

        return lista;
    }

    @Transactional
    public Productos getOne(String id) {
        Productos producto = productosRepositorio.getOne(id);
        return producto;
    }

    @Transactional
    public void eliminar(String id) {
        Productos producto = getOne(id);
        try {
            productosRepositorio.deleteById(id);
            System.err.println("producto eliminado");
        } catch (Exception e) {
            System.err.println("No se pudo eliminar el producto");
        }
    }

}
