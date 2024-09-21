package com.proyecto.okapii.controller;

import com.proyecto.okapii.model.Usuario;
import com.proyecto.okapii.repository.RepositorioUsuarios;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private RepositorioUsuarios muchosUsuarios;

    @PostMapping("/login")
    public String login(Model m, @RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes, HttpSession session) {
        Usuario u = muchosUsuarios.findByUsernameAndPassword(username, password);
        if (u == null) {
            redirectAttributes.addFlashAttribute("message", "Usuario y/o contraseña incorrectos.");
            return "index";
        } else {
            session.setAttribute("user", u);
        }
        return u.obtenerNombreVista();
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "index";
    }
}
