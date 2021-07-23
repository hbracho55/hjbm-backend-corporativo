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
@Entity(name = "Unidad")
@Table(name = "unidad")
public class Unidad {

    @Id
    @SequenceGenerator(
            name = "unidad_sequence",
            sequenceName = "unidad_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "unidad_sequence"
    )
    private Long id;

    @Column(
            name = "nombre",
            nullable = false
    )
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "id_padre")
    private Long idPadre;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_jerarquia",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "unidad_jerarquia_id_fk"
            )
    )
    private Jerarquia jerarquia;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_ubicacion",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "unidad_ubicacion_id_fk"
            )
    )
    private Ubicacion ubicacion;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_empresa",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "unidad_empresa_id_fk"
            )
    )
    private Empresa empresa;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_estado",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "unidad_estado_id_fk"
            )
    )
    private Estado estado;

    public Unidad(String nombre,
                  String descripcion,
                  Long idPadre,
                  Jerarquia jerarquia,
                  Ubicacion ubicacion,
                  Empresa empresa,
                  Estado estado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idPadre = idPadre;
        this.jerarquia = jerarquia;
        this.ubicacion = ubicacion;
        this.empresa = empresa;
        this.estado = estado;
    }
}