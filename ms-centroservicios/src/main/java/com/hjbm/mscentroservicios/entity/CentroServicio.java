package com.hjbm.mscentroservicios.entity;


import com.hjbm.mscentroservicios.model.Empresa;
import com.hjbm.mscentroservicios.model.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "centro_servicio")
public class CentroServicio {

    @Id
    @SequenceGenerator(
            name = "centro_servicio_sequence",
            sequenceName = "centro_servicio_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "centro_servicio_sequence"
    )
    private Long id;

    //@NotBlank
    private String nombre;
    private String descripcion;

    @Column(name = "id_empresa")
    private Integer idEmpresa;

    @Column(name = "id_estado")
    private Integer idEstado;

    @Transient
    private Empresa empresa;

    @Transient
    private Estado estado;

    public CentroServicio(String nombre,
                          String descripcion,
                          Integer idEmpresa,
                          Integer idEstado) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idEmpresa = idEmpresa;
        this.idEstado = idEstado;}
    }
