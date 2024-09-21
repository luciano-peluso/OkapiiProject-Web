package com.proyecto.okapii.controller;

import com.proyecto.okapii.model.Proyecto;
import com.proyecto.okapii.model.Usuario;
import com.proyecto.okapii.repository.RepositorioProyectos;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClienteController {

    @Autowired
    private RepositorioProyectos proyectos;

    @GetMapping("/misProyectosPage")
    public String irHaciaMisProyectos(Model m, HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Cliente")) {
            return usuarioLocal.obtenerNombreVista();
        }

        Usuario clienteLocal = (Usuario) session.getAttribute("user");

        List<Proyecto> misProyectos = proyectos.findAllByCliente(clienteLocal.getId());

        m.addAttribute("proyectos", misProyectos);

        return "mis_proyectos";
    }

}
