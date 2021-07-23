package com.hjbm.mscentroservicios.controller;


import com.hjbm.mscentroservicios.entity.CentroServicio;
import com.hjbm.mscentroservicios.service.CentroServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "centroservicio")
public class CentroServicioController {

    private final CentroServicioService centroServicioService;

    @Autowired
    public CentroServicioController(CentroServicioService centroServicioService) {
        this.centroServicioService = centroServicioService;
    }

    @GetMapping
    public List<CentroServicio> getCentroServicios(){

        return centroServicioService.obtenerCentroServicios();
    }

    @GetMapping(path = "/{id}")
    public CentroServicio getCentroServicio(Long idCentroServicio){

        CentroServicio centro= centroServicioService.obtenerCentroServicioById(idCentroServicio);
        return centro;
    }

}
