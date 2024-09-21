package com.proyecto.okapii.controller;

import com.proyecto.okapii.model.Cliente;
import com.proyecto.okapii.model.Desarrollador;
import com.proyecto.okapii.model.DevProyecto;
import com.proyecto.okapii.model.Proyecto;
import com.proyecto.okapii.model.Usuario;
import com.proyecto.okapii.model.UsuarioFactory;
import com.proyecto.okapii.repository.RepositorioClientes;
import com.proyecto.okapii.repository.RepositorioDesarrolladores;
import com.proyecto.okapii.repository.RepositorioDevsProyectos;
import com.proyecto.okapii.repository.RepositorioProyectos;
import com.proyecto.okapii.repository.RepositorioUsuarios;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private RepositorioUsuarios muchosUsuarios;
    @Autowired
    private RepositorioDesarrolladores muchosDesarrolladores;
    @Autowired
    private RepositorioProyectos muchosProyectos;
    @Autowired
    private RepositorioDevsProyectos devsProyectos;
    @Autowired
    private RepositorioClientes muchosClientes;

    @GetMapping("/regUserPage")
    public String irHaciaRegistrarUsuario(HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Admin")) {
            return usuarioLocal.obtenerNombreVista();
        }
        return "registrar_usuario";
    }

    @PostMapping("/registrarUsuario")
    public String crearUsuario(Model m, @RequestParam("username") String username, @RequestParam("password") String password,
            @RequestParam("rol") String rol) {

        Usuario nuevoUsuario = muchosUsuarios.findUsuarioByUsername(username);

        if (nuevoUsuario != null) {
            return "menu_admin";
        } else {
            Usuario usuarioNuevo = UsuarioFactory.crearUsuario(rol);

            usuarioNuevo.setUsername(username);
            usuarioNuevo.setPassword(password);
            usuarioNuevo.setRol(rol);

            muchosUsuarios.save(usuarioNuevo);

            m.addAttribute("usuarioRegistrado", true);
        }

        return "registrar_usuario";
    }

    @GetMapping("/regDevPage")
    public String irHaciaRegistrarDesarrollador(HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Admin")) {
            return usuarioLocal.obtenerNombreVista();
        }

        return "registrar_desarrollador";
    }

    @PostMapping("/registrarDesarrollador")
    public String registrarDesarrollador(Model m, @RequestParam("nombre") String nombre, @RequestParam("habilidades") String habilidades, @RequestParam("disp") int disp) {

        Desarrollador nuevoDesarrollador = muchosDesarrolladores.findByNombreAndHabilidades(nombre, habilidades);

        if (nuevoDesarrollador != null) {
            return "menu_admin";
        } else {
            Desarrollador desarrolladorNuevo = new Desarrollador();

            desarrolladorNuevo.setNombre(nombre);
            desarrolladorNuevo.setHabilidades(habilidades);
            desarrolladorNuevo.setDisponible(disp);

            muchosDesarrolladores.save(desarrolladorNuevo);

            m.addAttribute("desarrolladorRegistrado", true);
        }
        return "registrar_desarrollador";
    }

    @GetMapping("/devsDispPage")
    public String obtenerDesarrolladoresDisponibles(Model m, HttpSession session) {

        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Admin")) {
            return usuarioLocal.obtenerNombreVista();
        }

        List<Desarrollador> desarrolladoresDisponibles = muchosDesarrolladores.findByDisponible(0);

        m.addAttribute("desarrolladoresDisponibles", desarrolladoresDisponibles);

        return "desarrolladores_disponibles";
    }

    @GetMapping("/allUsersPage")
    public String irHaciaAllUsers(Model m, HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Admin")) {
            return usuarioLocal.obtenerNombreVista();
        }

        List<Usuario> todosLosUsuarios = muchosUsuarios.findAll();

        m.addAttribute("usuarios", todosLosUsuarios);

        return "all_users";
    }

    @GetMapping("/verAsignarDevs")
    public String verAsignarDevs(Model m, HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Admin")) {
            return usuarioLocal.obtenerNombreVista();
        }

        m.addAttribute("devs", muchosDesarrolladores.findByDisponible(0)); // Traer desarrolladores disponibles
        m.addAttribute("proyectos", muchosProyectos.findAllByEstado("No iniciado"));
        
        List<Proyecto> proyectos = muchosProyectos.findAllByEstado("No iniciado");
        List<String> nombres = new ArrayList<>();

        for (Proyecto unProyecto : proyectos) {
            Cliente c = muchosClientes.findClienteById(unProyecto.getCliente());
            nombres.add(c.getUsername());
        }

        m.addAttribute("nombres", nombres);

        return "asignar_devs";
    }

    @PostMapping("/asignarDesarrolladores")
    public String asignarDesarrolladores(Model m, @RequestParam("proyectoId") Long proyectoId,
            @RequestParam("desarrolladoresId") List<Long> desarrolladoresId) {
        
        // Crear en la tabla los registros correspondientes
        for (Long desarrolladorId : desarrolladoresId) {
            DevProyecto unDevProyecto = new DevProyecto();

            unDevProyecto.setProyecto(proyectoId);
            unDevProyecto.setDesarrollador(desarrolladorId);
            devsProyectos.save(unDevProyecto);

            Desarrollador dev = muchosDesarrolladores.findDevById(desarrolladorId);
            dev.setDisponible(1);
            muchosDesarrolladores.save(dev);
        }

        Proyecto unProyecto = muchosProyectos.findProyectoById(proyectoId);
        unProyecto.setEstado("En proceso");
        muchosProyectos.save(unProyecto);

        return "menu_admin";
    }
}
