package com.hjbm.mscentroservicios.service;

import com.hjbm.mscentroservicios.client.EmpresaClient;
import com.hjbm.mscentroservicios.model.Empresa;
import com.hjbm.mscentroservicios.model.Estado;
import com.hjbm.mscentroservicios.entity.CentroServicio;
import com.hjbm.mscentroservicios.repository.CentroServicioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CentroServicioService {

    private final CentroServicioRepository centroServicioRepository;
    private final EmpresaClient empresaClient;

    @Autowired
    public CentroServicioService(CentroServicioRepository centroServicioRepository,
                                 EmpresaClient empresaClient) {
        this.centroServicioRepository = centroServicioRepository;
        this.empresaClient = empresaClient;
    }

    public List<CentroServicio> obtenerCentroServicios() {

        List<CentroServicio> lista=centroServicioRepository.findAll();

        return lista.stream().map(centro -> {
                    Empresa empresa = empresaClient.getEmpresa(centro.getIdEmpresa()).getBody();
                    centro.setEmpresa(empresa);
                    return centro;
                }).collect(Collectors.toList());
    }

    public Optional<CentroServicio> obtenerCentroServicioById(Long idCentroServicio) {

        Optional<CentroServicio> centro = centroServicioRepository.findById(idCentroServicio);
        centro.get().setEmpresa(empresaClient.getEmpresa(centro.get().getIdEmpresa()).getBody());
        centro.get().setEstado(new Estado(1,"Activo","activo"));
        return centro;
    }

    public CentroServicio registrarCentroServicio(
            CentroServicio centroServicio) {

        if (centroServicio.getNombre() == null
                || centroServicio.getNombre().length() == 0){
            log.warn("No se puede registrar Cnetro de Servicio, nombre inválido.");
            throw new IllegalStateException("Nombre de Centro de Servicio inválido.");
        }

        Optional<CentroServicio> centroServicioOptional = centroServicioRepository
                .findByNombreAndEmpresa(centroServicio.getNombre(), centroServicio.getIdEmpresa());

        if (centroServicioOptional.isPresent()){
            log.warn("No se puede registrar CentroServicio, nombre ya registrado.");
            throw new IllegalStateException("Nombre de CentroServicio ya registrado.");
        }

        centroServicio.setIdEstado(1);
        centroServicioRepository.save(centroServicio);

        return centroServicio;
    }

    @Transactional
    public CentroServicio actualizarCentroServicio(
            Long idCentroServicio,
            String nombre,
            String descripcion,
            String iniciales,
            Integer idEstado) {

        CentroServicio centroServicio = centroServicioRepository.findById(idCentroServicio)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "CentroServicio con id " + idCentroServicio + " no está registrado.")
                );

        if (nombre != null
                && nombre.length() >0
                && !Objects.equals(centroServicio.getNombre(),nombre)){

            Optional<CentroServicio> centroServicioOptional = centroServicioRepository
                    .findByNombreAndEmpresa(nombre, centroServicio.getIdEmpresa());

            if (centroServicioOptional.isPresent()) {
                log.warn("CentroServicio con el nombre <" + nombre + "> ya está registrado.");
                new IllegalStateException(
                        "CentroServicio con el nombre <" + nombre + "> ya está registrado.");
            }
            centroServicio.setNombre(nombre);
        }

        if (descripcion != null
                && descripcion.length() >0
                && !Objects.equals(centroServicio.getDescripcion(),descripcion)){

            centroServicio.setDescripcion(descripcion);
        }

        return centroServicio;
    }
}

