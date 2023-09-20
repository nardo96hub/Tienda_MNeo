package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryConcepto extends JpaRepository<Concepto,Integer> {
}
