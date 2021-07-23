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
@Entity(name = "Sistema")
@Table(name = "sistema")
public class Sistema {

    @Id
    @SequenceGenerator(
            name = "sistema_sequence",
            sequenceName = "sistema_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sistema_sequence"
    )
    private Integer id;

    @Column(
            name = "nombre",
            nullable = false
    )
    private String nombre;

    @Column(
            name = "descripcion"
    )
    private String descripcion;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_empresa",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "sistema_empresa_id_fk"
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
                    name = "sistema_estado_id_fk"
            )
    )
    private Estado estado;

    public Sistema(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


}
