package com.hjbm.mscentroservicios.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "atencion")
public class Atencion {

    @Id
    @SequenceGenerator(
            name = "atencion_sequence",
            sequenceName = "atencion_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "atencion_sequence"
    )
    private Long id;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_solicitante")
    private Long idSolicitante;

    @Column(name = "fecha_elaboracion")
    private LocalDateTime fechaElaboracion;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "id_actividad_servicio")
    private Long idActividadServicio;

    @Column(name = "id_centro_servicio")
    private Long idCentroServicio;
}