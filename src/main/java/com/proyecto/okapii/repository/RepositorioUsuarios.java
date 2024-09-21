package com.proyecto.okapii.repository;

import com.proyecto.okapii.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuarios extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByUsername(String username);

    Usuario findByUsernameAndPassword(String username, String password);
}
