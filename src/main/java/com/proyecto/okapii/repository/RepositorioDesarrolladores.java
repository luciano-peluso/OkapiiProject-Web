package com.proyecto.okapii.repository;

import com.proyecto.okapii.model.Desarrollador;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDesarrolladores extends JpaRepository<Desarrollador, Long> {

    Desarrollador findByNombreAndHabilidades(String nombre, String habilidades);

    List<Desarrollador> findByDisponible(int disponible);

    Desarrollador findDevById(Long id);

}
