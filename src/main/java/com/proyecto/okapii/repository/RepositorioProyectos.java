package com.proyecto.okapii.repository;

import com.proyecto.okapii.model.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProyectos extends JpaRepository<Proyecto, Long> {

    Proyecto findProyectoByNombre(String nombre);

    List<Proyecto> findAllByCliente(int id);

    Proyecto findProyectoById(Long id);

    List<Proyecto> findAll();
    
    List<Proyecto> findAllByEstado(String estado);
    
    List<Proyecto> findAllByEstadoNot(String estado);
    

}
