package com.hjbm.msseguridad.repository;

import com.hjbm.msseguridad.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
