package com.hjbm.mscentroservicios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    private Integer id;
    private String nombre;
    private String descripcion;
    private String iniciales;

}
