package com.hjbm.msseguridad.repository;

import com.hjbm.msseguridad.entity.Jerarquia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JerarquiaRepository extends JpaRepository<Jerarquia, Integer> {
}
