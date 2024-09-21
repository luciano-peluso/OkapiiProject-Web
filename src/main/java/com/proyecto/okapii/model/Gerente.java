package com.proyecto.okapii.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Gerente")
public class Gerente extends Usuario {

    @Override
    public String obtenerNombreVista() {
        return "menu-gerente";
    }

}
