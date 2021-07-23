package com.hjbm.msseguridad.repository;

import com.hjbm.msseguridad.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    @Query("SELECT e FROM Empresa e WHERE e.nombre = ?1")
    Optional<Empresa> findByNombre(String nombre);
}
