package com.hjbm.msseguridad.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Estado")
@Table(name = "estado")
public class Estado {

    @Id
    @SequenceGenerator(
            name = "estado_sequence",
            sequenceName = "estado_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "estado_sequence"
    )
    private Integer id;

    @Column(
            name="nombre",
            nullable = false
    )
    private String nombre;

    @Column(
            name = "descripcion"
    )
    private String descripcion;

    public Estado(String nombre,
                  String descripcion) {

        this.nombre = nombre;
        this.descripcion = descripcion;
    }

}