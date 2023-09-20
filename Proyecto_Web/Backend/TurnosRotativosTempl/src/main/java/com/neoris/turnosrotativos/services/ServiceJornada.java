package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dto.JornadaDto;
import com.neoris.turnosrotativos.entities.Jornada;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public interface ServiceJornada {
    List<Jornada> obtenerJornadas(Integer nroDocumento, LocalDate fecha);
    Jornada crearJornada(JornadaDto jornada);
}
