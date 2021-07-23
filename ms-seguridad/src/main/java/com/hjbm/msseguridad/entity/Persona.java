package com.hjbm.msseguridad.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Persona")
@Table(name = "persona",
        uniqueConstraints = {
                @UniqueConstraint( name = "persona_email_unique", columnNames = "email")
        })
public class Persona{

    @Id
    @SequenceGenerator(
            name = "persona_sequence",
            sequenceName = "persona_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "persona_sequence"
    )
    private Long id;

    @Column(
            name = "nombre",
            nullable = false
    )
    private String nombre;

    @Column(
            name = "apellido",
            nullable = false
    )
    private String apellido;

    @Email(message = "Formato de email incorrecto")
    @Column(
            name = "email",
            nullable = false
    )
    private String email;

    @Column(
            name = "nro_ident"
    )
    private String nroIdent;

    @Column(
            name = "nro_telef"
    )
    private String nroTelef;

    @Column(
            name = "nro_telef_interno"
    )
    private String nroTelefInterno;

    @Column(
            name = "nro_empleado"
    )
    private String nroEmpleado;

    @Column(
            name = "cargo"
    )
    private String cargo;

    @Column(
            name = "direccion"
    )
    private String direccion;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_ubicacion",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "persona_ubicacion_id_fk"
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
                    name = "persona_empresa_id_fk"
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
                    name = "persona_estado_id_fk"
            )
    )
    private Estado estado;

}
