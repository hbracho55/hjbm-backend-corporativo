package com.hjbm.msseguridad.repository;

import com.hjbm.msseguridad.entity.Unidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Long> {
}
