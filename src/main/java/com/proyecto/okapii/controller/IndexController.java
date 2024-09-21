package com.proyecto.okapii.controller;

import com.proyecto.okapii.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(HttpSession session, Model modelo) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        }
        //modelo.addAllAttributes(usuarioLocal.obtenerElementos());
        return usuarioLocal.obtenerNombreVista();
    }
}
