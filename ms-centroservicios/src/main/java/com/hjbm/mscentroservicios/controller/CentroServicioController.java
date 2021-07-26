package com.hjbm.mscentroservicios.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjbm.mscentroservicios.entity.CentroServicio;
import com.hjbm.mscentroservicios.exception.MensajeError;
import com.hjbm.mscentroservicios.service.CentroServicioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "centroservicios")
public class CentroServicioController {

    private final CentroServicioService centroServicioService;

    @Autowired
    public CentroServicioController(CentroServicioService centroServicioService) {
        this.centroServicioService = centroServicioService;
    }

    @GetMapping
    public ResponseEntity<List<CentroServicio>> getCentroServicios(){

        List centros = new ArrayList<>();
        centros = centroServicioService.obtenerCentroServicios();
        if (centros.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(centros);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CentroServicio> getCentroServicio(@PathVariable("id") Long id){

        Optional<CentroServicio> centroServicio = centroServicioService.obtenerCentroServicioById(id);
        if (!centroServicio.isPresent()){
            log.error("Centro de servicio con id {} no encontrado.",id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(centroServicio.get());

    }

    @PostMapping
    public ResponseEntity<CentroServicio> incluirCentroServicio(@Valid @RequestBody CentroServicio centroServicio,
                                                                BindingResult result){

        if (result.hasErrors()){
            String errores=this.formatoMensaje(result);
            log.error("Se presentaron los siguientes errores de validación al registrar el Centro de Servicio: {}",errores);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errores);
        }

        CentroServicio centroServicioRegistrado=centroServicioService.registrarCentroServicio(centroServicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(centroServicioRegistrado);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CentroServicio> actualizarCentroServicio(@PathVariable("id") Long id,
                                                     @RequestParam String nombre,
                                                     @RequestParam(required = false) String descripcion,
                                                     @RequestParam(required = false) String iniciales,
                                                     @RequestParam(required = false) Integer idEstado){

        CentroServicio centroServicioActualizado = centroServicioService
                .actualizarCentroServicio(id, nombre, descripcion, iniciales, idEstado);
        if (centroServicioActualizado==null){
            log.error("No es posible actualizar. Centro de Servicio con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(centroServicioActualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> eliminarCentroServicio(@PathVariable("id") Long id){
        boolean eliminado= false;
        //eliminado=centroServicioService.eliminarCentroServicio(id);
        if (!eliminado){
            log.error("No es posible eliminar. Centro de Servicio con id {} no encontrado.", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eliminado);
    }

    private String formatoMensaje(BindingResult result){
        List<Map<String,String>> errores=result.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String,String> error = new HashMap<>();
                    error.put(fieldError.getField(),fieldError.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        MensajeError mensajeError = MensajeError.builder()
                .codigo("01")
                .mensajes(errores).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try{
            jsonString=mapper.writeValueAsString(mensajeError);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

}
