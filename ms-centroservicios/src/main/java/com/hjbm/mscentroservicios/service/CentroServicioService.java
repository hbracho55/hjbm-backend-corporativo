package com.hjbm.mscentroservicios.service;

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

    @Autowired
    public CentroServicioService(CentroServicioRepository centroServicioRepository) {

        this.centroServicioRepository = centroServicioRepository;
    }

    public List<CentroServicio> obtenerCentroServicios() {

        List<CentroServicio> lista=centroServicioRepository.findAll();
/*
        return lista.stream().map(c ->
                        c.setEmpresa(empresaClient.getEmpresa(c.getIdEmpresa()).get())
                ).collect(Collectors.toList());
*/
        return lista;

    }

    public CentroServicio obtenerCentroServicioById(Long idCentroServicio) {

        Optional<CentroServicio> c = centroServicioRepository.findById(idCentroServicio);
        //c.get().setEmpresa(empresaClient.getEmpresa(c.get().getIdEmpresa()).get());
        c.get().setEstado(new Estado(1,"Activo","activo"));
        return c.get();
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
            String descripcion) {

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

