package com.hjbm.msseguridad.service;

import com.hjbm.msseguridad.entity.Empresa;
import com.hjbm.msseguridad.entity.Estado;
import com.hjbm.msseguridad.repository.EmpresaRepository;
import com.hjbm.msseguridad.repository.EstadoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EstadoRepository estadoRepository;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository,
                          EstadoRepository estadoRepository) {

        this.empresaRepository = empresaRepository;
        this.estadoRepository = estadoRepository;
    }

    public List<Empresa> obtenerEmpresas() {

        return empresaRepository.findAll();

    }

    public Optional<Empresa> obtenerEmpresaPorId(Integer idEmpresa) {

        Optional<Empresa> empresa = empresaRepository.findById(idEmpresa);
        return empresa;
    }

    public Empresa registrarEmpresa(Empresa empresa) {

        if (empresa.getNombre() == null
                || empresa.getNombre().length() == 0){
            log.warn("No se puede registrar Empresa, nombre inválido.");
            throw new IllegalStateException("Nombre de Empresa inválido.");
        }

        Optional<Empresa> empresaOptional = empresaRepository.findByNombre(empresa.getNombre());

        if (empresaOptional.isPresent()){
            log.warn("No se puede registrar Empresa, nombre ya registrado.");
            throw new IllegalStateException("Empresa ya registrada.");
        }

        Optional<Estado> estado =estadoRepository.findById(1);
        empresa.setEstado(estado.get());
        empresaRepository.save(empresa);

        return empresa;
    }

    @Transactional
    public Empresa actualizarEmpresa(
            Integer idEmpresa,
            String nombre,
            String descripcion,
            String iniciales) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Empresa con id " + idEmpresa + " no está registrada.")
                );

        if (nombre != null
                && nombre.length() >0
                && !Objects.equals(empresa.getNombre(),nombre)){

            Optional<Empresa> empresaOptional = empresaRepository.findByNombre(nombre);

            if (empresaOptional.isPresent()) {
                log.warn("Empresa con el nombre <" + nombre + "> ya está registrada.");
                new IllegalStateException(
                        "Empresa con el nombre <" + nombre + "> ya está registrada.");
            }
            empresa.setNombre(nombre);
        }

        if (descripcion != null
                && descripcion.length() >0
                && !Objects.equals(empresa.getDescripcion(),descripcion)){

            empresa.setDescripcion(descripcion);
        }

        if (iniciales != null
                && iniciales.length() > 0
                && !Objects.equals(empresa.getIniciales(),iniciales)){

            empresa.setIniciales(iniciales);
        }

        return empresa;
    }

    public boolean eliminarEmpresa(Integer idEmpresa) {

        boolean existe = empresaRepository.existsById(idEmpresa);
        if (!existe){
            throw new IllegalStateException("Empresa con id "+ idEmpresa + " no existe");
        }
        empresaRepository.deleteById(idEmpresa);

        return true;
    }
}
