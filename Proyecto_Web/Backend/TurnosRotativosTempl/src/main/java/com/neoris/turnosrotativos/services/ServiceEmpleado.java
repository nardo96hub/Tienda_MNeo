package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dto.EmpleadoDto;
import com.neoris.turnosrotativos.entities.Empleado;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ServiceEmpleado {

     Empleado crearEmpleado(Empleado empleado); // Crea un nuevo Empleado
     List<Empleado> obtenerEmpleados(); // Retorna una lista de todos los Empleados
     Empleado obtenerEmpleadoId(Integer id); // Retorna empleado que coincide con Id
     Empleado actualizarEmpleado(Integer id, EmpleadoDto empleado); // Actualiza informacion de Empleado segun el id
     void eliminarEmpleado(Integer id); // Elimino un Empleado

}
