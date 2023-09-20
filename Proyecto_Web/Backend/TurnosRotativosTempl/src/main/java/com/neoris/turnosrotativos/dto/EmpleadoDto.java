package com.neoris.turnosrotativos.dto;


import com.neoris.turnosrotativos.entities.Empleado;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data // Lombok
public class EmpleadoDto {

    @NotNull(message = "'nroDocumento' es obligatorio")
    private Integer nroDocumento;

    @NotNull(message = "'nombre' es obligatorio")
    @NotBlank(message = "'nombre' esta vacio")
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

    public Empleado toEntity(){
        Empleado empleado=new Empleado();

        empleado.setNroDocumento(this.nroDocumento);
        empleado.setNombre(this.nombre);
        empleado.setApellido(this.apellido);
        empleado.setEmail(this.email);
        empleado.setFechaNacimiento(this.fechaNacimiento);
        empleado.setFechaIngreso(this.fechaIngreso);

        return empleado;
    }

}
