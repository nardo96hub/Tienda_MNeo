package com.neoris.turnosrotativos.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Entity(name = "jornadas")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // GenerationType.IDENTITY id se incrementa en 1
    private Integer id;

    private Integer nroDocumento;

    @NotNull(message = "'nombreCompleto' es obligatorio")
    @NotBlank(message = "'nombreCompleto' esta vacio")
    @Pattern(regexp ="[a-zA-Z\\s]+" ,message ="Solo se permiten letras en el campo 'nombreCompleto'" )
    private String nombreCompleto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @NotNull(message = "'concepto' es obligatorio")
    @NotBlank(message = "'concepto' esta vacio")
    @Pattern(regexp ="[a-zA-Z\\s]+" ,message ="Solo se permiten letras en el campo 'concepto'" )
    private String concepto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer hsTrabajadas;

    public Jornada(){}

}
