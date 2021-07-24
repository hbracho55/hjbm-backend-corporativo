package com.hjbm.msseguridad.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Empresa")
@Table(name = "empresa")
public class Empresa {

    @Id
    @SequenceGenerator(
            name = "empresa_sequence",
            sequenceName = "empresa_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "empresa_sequence"
    )
    private Integer id;

    @NotEmpty(message = "El nombre de la Empresa no puede estar en blanco")
    @Column(
            name = "nombre",
            nullable = false
    )
    private String nombre;

    @Column(
            name = "descripcion"
    )
    private String descripcion;

    @Column(
            name = "iniciales"
    )
    private String iniciales;

    @NotNull(message = "La empresa debe tener un estado")
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_estado",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "empresa_estado_id_fk"
            )
    )
    private Estado estado;

    public Empresa(String nombre,
                   String descripcion,
                   String iniciales,
                   Estado estado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.iniciales = iniciales;
        this.estado = estado;
    }

}
