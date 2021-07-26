package com.hjbm.mscentroservicios.client;

import com.hjbm.mscentroservicios.model.Empresa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@FeignClient(name = "servicio-seguridad")
@RequestMapping(path = "empresas")
public interface EmpresaClient {

    @GetMapping
    public ResponseEntity<List<Empresa>> getEmpresas();

    @GetMapping(path = "/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable("id") Integer idEmpresa);


}

