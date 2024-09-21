package com.proyecto.okapii.repository;

import com.proyecto.okapii.model.DevProyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDevsProyectos extends JpaRepository<DevProyecto, Long> {

    List<DevProyecto> findAll();

    List<DevProyecto> findAllByProyecto(Long idProyecto);
}
