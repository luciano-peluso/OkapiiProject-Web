package com.proyecto.okapii.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;

@Entity
@Table(name = "desarrolladores")
@Data
public class Desarrollador implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private int id;

    @Column(length = 32, nullable = false)
    private String nombre;

    @Column(length = 1, nullable = false)
    private int disponible;

    @Column(length = 200, nullable = false)
    private String habilidades;

}
