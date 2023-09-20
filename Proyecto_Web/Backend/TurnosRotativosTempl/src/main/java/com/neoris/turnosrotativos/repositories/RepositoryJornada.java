package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositoryJornada extends JpaRepository<Jornada,Integer> {

   //Retorna todas las jornadas que coincida la fecha
   List<Jornada> findAllByFecha(LocalDate fecha);

   //Retorna todas las jornadas que coincida el dni
   @Query(value="SELECT j FROM jornadas j WHERE j.nroDocumento=:dni" )
   List<Jornada> findAllByNroDocumento(@Param("dni") Integer dni);

   // //Retorna todas las jornadas que coincida el dni y la fecha
   @Query(value = "SELECT j FROM jornadas j WHERE j.nroDocumento=:dni AND j.fecha=:fecha")
   List<Jornada> findAllCondicion(@Param("dni") Integer dni,@Param("fecha") LocalDate fecha);

   // //Retorna todas las jornadas que coincida el dni y ademas la fecha coincida en algun dia de la Semana
   @Query(value= "SELECT j FROM jornadas j WHERE j.nroDocumento=:dni AND j.fecha BETWEEN :lunes AND :domingo")
   List<Jornada> findAllCondicionRangoFecha(@Param("dni") Integer dni,@Param("lunes") LocalDate lunes,@Param("domingo") LocalDate domingo);
}
