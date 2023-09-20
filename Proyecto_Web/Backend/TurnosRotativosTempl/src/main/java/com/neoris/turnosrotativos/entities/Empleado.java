package com.neoris.turnosrotativos.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data // Lombok
@Entity(name="empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // GenerationType.IDENTITY id se incrementa en 1
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer nroDocumento;

    @NotNull(message = "'nombre' es obligatorio")
    @NotBlank (message = "'nombre' esta vacio")
    @Pattern(regexp ="[a-zA-Z]+" ,message ="Solo se permiten letras en el campo 'nombre'" )
    private String nombre;

    @NotNull (message = "'apellido' es obligatorio")
    @NotBlank (message = "'apellido' esta vacio")
    @Pattern(regexp ="[a-zA-Z]+" ,message ="Solo se permiten letras en el campo 'apellido'" )
    private String apellido;
    @NotNull (message = "'email' es obligatorio")
    @NotBlank (message = "'email' esta vacio")
    @Email(message = "El email ingresado no es correcto" )
    private String email;

    @NotNull (message = "'fechaNacimiento' es obligatorio")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    @NotNull(message = "'fechaIngreso' es obligatorio")

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCreacion;


    public Empleado(){

    }
   public Empleado(Integer nroDocumento,String nombre,String apellido,String email,LocalDate fechaNacimiento,LocalDate fechaIngreso,LocalDate fechaCreacion){
        this.nroDocumento=nroDocumento;
        this.nombre=nombre;
        this.apellido=apellido;
        this.email=email;
        this.fechaNacimiento=fechaNacimiento;
        this.fechaIngreso=fechaIngreso;
        this.fechaCreacion=fechaCreacion;

    }
}
