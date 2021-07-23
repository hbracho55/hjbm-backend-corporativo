package com.hjbm.msseguridad.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hjbm.msseguridad.entity.Empresa;
import com.hjbm.msseguridad.exception.MensajeError;
import com.hjbm.msseguridad.service.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresas(){

        List empresas = new ArrayList<>();
        empresas = empresaService.obtenerEmpresas();
        if (empresas.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable("id") Integer idEmpresa){

        Optional<Empresa> optionalEmpresa= empresaService.obtenerEmpresaPorId(idEmpresa);
        if (!optionalEmpresa.isPresent()){
            log.error("Empresa con id {} no encontrada.",idEmpresa);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalEmpresa.get());

    }

    @PostMapping
    public ResponseEntity<Empresa> incluirEmpresa(@Valid @RequestBody Empresa empresa, BindingResult result){

        if (result.hasErrors()){
            String errores=this.formatoMensaje(result);
            log.error("Se presentaron los siguientes errores de validación al registrar empresa: {}",errores);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errores);
        }

        Empresa empresaRegistrada=empresaService.registrarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaRegistrada);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable("id") Integer idEmpresa,
                                  @RequestParam String nombre,
                                  @RequestParam(required = false) String descripcion,
                                  @RequestParam(required = false) String iniciales){

        Empresa empresaActualizada=empresaService.actualizarEmpresa(idEmpresa, nombre, descripcion, iniciales);
        if (empresaActualizada==null){
            log.error("No es posible actualizar. Empresa con id {} no encontrada", idEmpresa);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresaActualizada);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> eliminarEmpresa(@PathVariable("id") Integer idEmpresa){
        boolean eliminado= false;
        eliminado=empresaService.eliminarEmpresa(idEmpresa);
        if (!eliminado){
            log.error("No es posible eliminar. Empresa con id {} no encontrada", idEmpresa);
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

