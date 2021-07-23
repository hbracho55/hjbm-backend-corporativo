package com.hjbm.msseguridad.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Usuario")
@Table(name = "usuario",
        uniqueConstraints = {
                @UniqueConstraint( name = "usuario_nombre_unique", columnNames = "nombre")
        })
public class Usuario  {

    @Id
    @SequenceGenerator(
            name = "usuario_sequence",
            sequenceName = "usuario_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_sequence"
    )
    private Long id;

    @Size(min = 4, max = 20, message = "el nombre de usuario debe ser d elongitud mínimo 4 y máxima 20")
    @Column(
            name = "nombre",
            nullable = false
    )
    private String nombre;

    @Column(
            name = "clave",
            nullable = false
    )
    private String clave;

    @Column(
            name = "pregunta_secreta",
            nullable = false
    )
    private String preguntaSecreta;

    @Column(
            name = "respuesta_secreta",
            nullable = false
    )
    private String respuestaSecreta;

    @Column(
            name = "expira_clave",
            nullable = false
    )
    private Boolean expiraClave;

    @Column(
            name = "fecha_expiracion",
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDate fechaExpiracion;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_persona",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "usuario_persona_id_fk"
            )
    )
    private Persona persona;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = false
    )
    @JoinColumn(
            name = "id_empresa",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "usuario_empresa_id_fk"
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
                    name = "usuario_estado_id_fk"
            )
    )
    private Estado estado;

    public Usuario(String nombre,
                   String clave,
                   String preguntaSecreta,
                   String respuestaSecreta,
                   Boolean expiraClave,
                   LocalDate fechaExpiracion,
                   Persona persona,
                   Empresa empresa,
                   Estado estado) {

        this.nombre = nombre;
        this.clave = clave;
        this.preguntaSecreta = preguntaSecreta;
        this.respuestaSecreta = respuestaSecreta;
        this.expiraClave = expiraClave;
        this.fechaExpiracion = fechaExpiracion;
        this.persona = persona;
        this.empresa = empresa;
        this.estado = estado;
    }

}
