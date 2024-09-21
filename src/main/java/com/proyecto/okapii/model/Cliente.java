package com.proyecto.okapii.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Cliente")
public class Cliente extends Usuario {

    @Override
    public String obtenerNombreVista() {
        return "cliente";
    }

}
