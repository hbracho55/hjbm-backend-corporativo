package com.hjbm.mscentroservicios.repository;

import com.hjbm.mscentroservicios.entity.CentroServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CentroServicioRepository extends JpaRepository<CentroServicio, Long> {

    @Query("SELECT c FROM CentroServicio WHERE c.nombre=?1 AND Empresa.id=?2")
    Optional<CentroServicio> findByNombreAndEmpresa(String nombre, Integer idEmpresa);
}