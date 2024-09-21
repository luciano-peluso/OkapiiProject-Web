package com.proyecto.okapii.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Usuario {

    @Override
    public String obtenerNombreVista() {
        return "menu_admin";
    }

}
