package com.proyecto.okapii.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(length = 32, nullable = false)
    private String nombre;

    @Column(length = 32, nullable = false)
    private String estado;

    @Column(length = 200, nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private int presupuesto;

    @Column(nullable = false)
    private Date fecha_inicio;

    @Column(nullable = true)
    private Date fecha_final;

    @Column(name = "cliente", nullable = false)
    private int cliente;
}
