package com.tutoria.local.servicios;

import com.tutoria.local.entidades.Usuario;
import com.tutoria.local.enums.Rol;
import com.tutoria.local.excepciones.excepciones;
import com.tutoria.local.repositorios.usuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class usuarioServicio implements UserDetailsService {

    @Autowired
    private usuarioRepositorio usuarioRepositorio;

    @Transactional
    public void crearUsuario(String nombre, String email, String password, String password2) throws excepciones {
        System.out.println("vamos a validar los datosS");
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        
        if (nombre.equals("admin")) {
        usuario.setRol(Rol.DUEÑO);    
        }else{
            usuario.setRol(Rol.CLIENTE);
        }
        
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    private void validar( String nombre, String email, String password, String password2) throws excepciones {

        if (nombre == null || nombre.isEmpty()) {
            throw new excepciones("nombre nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new excepciones("email nulo");
        }
        if (password == null || password.isEmpty()) {
            throw new excepciones("password nulo");
        }
        if (!password.equals(password2)) {
            throw new excepciones("Las contraseñas no coinciden");
        }

    }

    @Transactional
    public void actualizarUsuario(String id, MultipartFile foto, String nombre, String email, String password, String password2)
            throws excepciones {
        validar(nombre, email, password, password2);
        Usuario usuario = getOne(id);
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public Usuario getOne(String id) {
        Usuario usuario = usuarioRepositorio.getOne(id);
        return usuario;
    }

    @Transactional
    public List<Usuario> listar() {
        List<Usuario> lista = usuarioRepositorio.findAll();

        return lista;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

    @Transactional
    public void cambiarRol(String id) {
        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            if (usuario.getRol().toString().equals("USER")) {
                usuario.setRol(Rol.DUEÑO);
            } else if (usuario.getRol().toString().equals("ADMIN")) {
                usuario.setRol(Rol.CLIENTE);
            }
        } else {
            System.err.println("el usuario no se encontro");
        }
    }

    
}
