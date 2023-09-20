package com.neoris.turnosrotativos.dto.auxiliar;

import lombok.Data;

import java.time.LocalDate;

@Data //Lombok
public class Semana {
    private LocalDate lunes;
    private LocalDate domingo;

    public Semana(LocalDate fecha){

        // Segun el dia de la semana asigno Lunes y Domingo aplicando operaciones entre fechas
        switch(fecha.getDayOfWeek()){
            case MONDAY:
                this.lunes=fecha;
                this.domingo=fecha.plusDays(6);
                break;
            case TUESDAY:
                this.lunes=fecha.minusDays(1);
                this.domingo=fecha.plusDays(5);
                break;
            case WEDNESDAY:
                this.lunes=fecha.minusDays(2);
                this.domingo=fecha.plusDays(4);
                break;
            case THURSDAY:
                this.lunes =fecha.minusDays(3);
                this.domingo=fecha.plusDays(3);
                break;
            case FRIDAY:
                this.lunes=fecha.minusDays(4);
                this.domingo=fecha.plusDays(2);
                break;
            case SATURDAY:
                this.lunes=fecha.minusDays(5);
                this.domingo=fecha.plusDays(1);
                break;
            default:
                this.lunes=fecha.minusDays(6);
                this.domingo=fecha;
                break;
        }
    }
}
