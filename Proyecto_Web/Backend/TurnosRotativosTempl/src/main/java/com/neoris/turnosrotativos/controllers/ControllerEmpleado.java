package com.neoris.turnosrotativos.controllers;



import com.neoris.turnosrotativos.dto.EmpleadoDto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.services.ServiceEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class ControllerEmpleado {
    @Autowired
    private ServiceEmpleado serviEmpleado;


    @PostMapping("empleado")
    public ResponseEntity<Empleado> crearEmpleado(@Valid @RequestBody Empleado empleado){
        Empleado empleadoAdd=serviEmpleado.crearEmpleado(empleado);
        HttpHeaders responseHeaders=new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION,String.format("/empleado/%d",empleadoAdd.getId()));
        return ResponseEntity.created(URI.create(String.format("/empleado/%d",empleadoAdd.getId()))).body(empleadoAdd);

    }

    @GetMapping("empleado")
    public ResponseEntity<List<Empleado>> obtenerEmpleados(){

        return ResponseEntity.ok(serviEmpleado.obtenerEmpleados());
    }

    @GetMapping("empleado/{empleadoId}")
    public ResponseEntity<Empleado> obtenerEmpleado(@PathVariable("empleadoId") Integer id){
        return ResponseEntity.ok(serviEmpleado.obtenerEmpleadoId(id));
    }

    @PutMapping("empleado/{empleadoId}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable("empleadoId") Integer id,@Valid @RequestBody EmpleadoDto empleado){
        Empleado empleadoUpdate=serviEmpleado.actualizarEmpleado(id,empleado);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


    //@RequestMapping(value = "/empleado/{empleadoId}",method = RequestMethod.DELETE)
    @DeleteMapping("empleado/{empleadoId}")
    public ResponseEntity<Objects> borrarEmpleado(@PathVariable("empleadoId") Integer id){
        serviEmpleado.eliminarEmpleado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
