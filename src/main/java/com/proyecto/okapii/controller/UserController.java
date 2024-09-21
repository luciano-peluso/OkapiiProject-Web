package com.proyecto.okapii.controller;

import com.proyecto.okapii.model.Usuario;
import com.proyecto.okapii.repository.RepositorioUsuarios;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private RepositorioUsuarios muchosUsuarios;

    @GetMapping("/verCambiarPassword")
    public String verCambiarPassword(HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        }
        return "cambiar_password";
    }

    @PostMapping("/cambiarPassword")
    public String cambiarPassword(HttpSession session, @RequestParam("passwordViejo") String passwordViejo,
            @RequestParam("passwordNuevo") String passwordNuevo, Model m) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");

        if (usuarioLocal != null && usuarioLocal.getPassword().equals(passwordViejo)) {
            usuarioLocal.setPassword(passwordNuevo);
            muchosUsuarios.saveAndFlush(usuarioLocal);
            m.addAttribute("passwordCambiada", true);
        } else {
            m.addAttribute("passwordFallo", true);
        }

        return "cambiar_password";
    }
}
