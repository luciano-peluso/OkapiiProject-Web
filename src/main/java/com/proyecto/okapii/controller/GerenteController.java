package com.proyecto.okapii.controller;

import com.proyecto.okapii.model.Cliente;
import com.proyecto.okapii.model.Desarrollador;
import com.proyecto.okapii.model.DevProyecto;
import com.proyecto.okapii.model.Proyecto;
import com.proyecto.okapii.model.Usuario;
import com.proyecto.okapii.repository.RepositorioClientes;
import com.proyecto.okapii.repository.RepositorioDesarrolladores;
import com.proyecto.okapii.repository.RepositorioDevsProyectos;
import com.proyecto.okapii.repository.RepositorioProyectos;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GerenteController {

    @Autowired
    private RepositorioProyectos muchosProyectos;

    @Autowired
    private RepositorioClientes muchosClientes;

    @Autowired
    private RepositorioDevsProyectos devsProyectos;

    @Autowired
    private RepositorioDesarrolladores muchosDevs;

    @PostMapping("/guardarProyecto")
    public String guardarProyecto(@ModelAttribute Proyecto proyecto, Model m) {

        Proyecto nuevoProyecto = muchosProyectos.findProyectoByNombre(proyecto.getNombre());

        if (nuevoProyecto != null) {
            return "menu_gerente";
        }
        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        proyecto.setFecha_inicio(date);
        proyecto.setFecha_final(null);
        muchosProyectos.save(proyecto);

        m.addAttribute("proyectoCreado", true);

        return "redirect:/nuevoProyecto";
    }

    @GetMapping("/nuevoProyecto")
    public String nuevoProyecto(Model m, HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Gerente")) {
            return usuarioLocal.obtenerNombreVista();
        }

        m.addAttribute("clientes", muchosClientes.findAll());

        return "nuevo_proyecto";
    }

    @GetMapping("/verProyectos")
    public String verProyectos(Model m, HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Gerente")) {
            return usuarioLocal.obtenerNombreVista();
        }

        List<Proyecto> proyectos = muchosProyectos.findAllByEstadoNot("Finalizado");
        m.addAttribute("proyectos", proyectos);

        List<String> nombres = new ArrayList<>();

        for (Proyecto unProyecto : proyectos) {
            Cliente c = muchosClientes.findClienteById(unProyecto.getCliente());
            nombres.add(c.getUsername());
        }

        m.addAttribute("nombres", nombres);
        return "ver_proyectos";
    }

    @GetMapping("/proyectos/{id}/editar")
    public String editarProyecto(@PathVariable Long id, Model model, HttpSession session) {
        Usuario usuarioLocal = (Usuario) session.getAttribute("user");
        if (usuarioLocal == null) {
            return "index";
        } else if (!usuarioLocal.getRol().equals("Gerente")) {
            return usuarioLocal.obtenerNombreVista();
        }

        Proyecto proyecto = muchosProyectos.findProyectoById(id);
        model.addAttribute("proyecto", proyecto);
        List<Cliente> clientes = muchosClientes.findAll();
        model.addAttribute("clientes", clientes);
        return "editar_proyecto";
    }

    @PostMapping("/confirmarCambios")
    public String confirmarCambios(@ModelAttribute Proyecto proyecto, Model m) {

        Proyecto unProyecto = muchosProyectos.findProyectoById(proyecto.getId());

        unProyecto.setNombre(proyecto.getNombre());
        unProyecto.setDescripcion(proyecto.getDescripcion());
        unProyecto.setPresupuesto(proyecto.getPresupuesto());
        unProyecto.setCliente(proyecto.getCliente());

        muchosProyectos.saveAndFlush(unProyecto);

        m.addAttribute("proyectoEditado", true);
        return "editar_proyecto";
    }

    @PostMapping("/finalizarProyecto")
    public String finalizarProyecto(@RequestParam("id") Long id, Model m) {

        Proyecto unProyecto = muchosProyectos.findProyectoById(id);

        unProyecto.setEstado("Finalizado");

        LocalDate localDate = LocalDate.now();
        Date date = java.sql.Date.valueOf(localDate);

        unProyecto.setFecha_final(date);

        muchosProyectos.saveAndFlush(unProyecto);

        List<DevProyecto> muchosDevsProyectos = devsProyectos.findAllByProyecto(unProyecto.getId());

        for (DevProyecto unDevProyecto : muchosDevsProyectos) {
            Desarrollador unDev = muchosDevs.findDevById(unDevProyecto.getDesarrollador());

            unDev.setDisponible(0);

            muchosDevs.saveAndFlush(unDev);
        };

        devsProyectos.deleteAllInBatch(muchosDevsProyectos);

        return "redirect:/verProyectos";
    }

}
