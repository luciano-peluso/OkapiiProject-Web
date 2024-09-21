package com.proyecto.okapii.repository;

import com.proyecto.okapii.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioClientes extends JpaRepository<Cliente, Long> {

    Cliente findClienteById(int id);
}
