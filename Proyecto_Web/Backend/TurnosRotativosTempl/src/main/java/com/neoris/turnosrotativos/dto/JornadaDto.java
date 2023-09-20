package com.neoris.turnosrotativos.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data //Lombok
public class JornadaDto {
    @NotNull(message = "'idEmpleado' es obligatorio")

    private Integer idEmpleado;
    @NotNull(message = "'idConcepto' es obligatorio")
    private Integer idConcepto;
    @NotNull(message = "'fecha' es obligatorio")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private Integer horasTrabajadas;


    // Asignacion de valores a los atributos de Jornada
    public Jornada toEntity(Empleado e, Concepto c){
        Jornada j=new Jornada();
        j.setConcepto(c.getNombre());
        j.setFecha(this.getFecha());
        j.setNombreCompleto(e.getNombre()+" "+e.getApellido());
        j.setNroDocumento(e.getNroDocumento());
        j.setHsTrabajadas(this.horasTrabajadas);
        return j;
    }

}
