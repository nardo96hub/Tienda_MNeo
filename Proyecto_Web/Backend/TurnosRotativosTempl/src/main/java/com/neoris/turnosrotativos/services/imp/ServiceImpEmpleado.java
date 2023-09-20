package com.neoris.turnosrotativos.services.imp;

import com.neoris.turnosrotativos.dto.EmpleadoDto;
import com.neoris.turnosrotativos.exception.Excepciones;
import com.neoris.turnosrotativos.services.ServiceEmpleado;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.repositories.RepositoryEmpleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceImpEmpleado implements ServiceEmpleado {

    @Autowired
    private RepositoryEmpleado repoEmpleado;

    // Funcion privada que analiza si los datos son validos, en el caso que no sea ocurre excepcion, retorna true si no ocurre nada
    /**
     *
     * @param emp :Empleado  recibo los datos del empleado para analizar
     * @param creacion:LocalDate  fecha de creacion del empleado
     * @return :Boolean  si no ocurre ninguna excepcion  retorna true
     */
    private Boolean analizar(Empleado emp,LocalDate creacion){
        //Analizo si fechaNacimiento > fechaCreacion
        if(creacion.compareTo(emp.getFechaNacimiento())<0) {
            throw new Excepciones("La fecha de nacimiento no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            //Analizo si el Documento ingresado pertenece a Empleados
        }else if(repoEmpleado.findByNroDocumento(emp.getNroDocumento()).isPresent()){
                throw new Excepciones("Ya existe un empleado con el documento ingresado.", HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.name());
            //Analizo si el Email ingresado pertenece a Empleados
            }else if(repoEmpleado.findByEmail(emp.getEmail()).isPresent()){
                    throw new Excepciones("Ya existe un empleado con el email ingresado.", HttpStatus.CONFLICT.value(),HttpStatus.CONFLICT.name());

                    //Analizo si fechaNacimiento < 18
                        //Period.between().getYears() Retorna la diferencia de años entre 2 fechas
                }else if(Period.between(emp.getFechaNacimiento(),creacion).getYears()<18){
                        throw new Excepciones("La edad del empleado no puede ser menor a 18 años.", HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name());

                    //Analizo si fechaIngreso > fechaCreacion
                        // creacion.compareTo(emp.getFechaIngreso())  Si la fecha creacion es mayor retorna >0 sino retorna <0
                    }else if (creacion.compareTo(emp.getFechaIngreso())<0){
                            throw new Excepciones("La fecha de ingreso no puede ser posterior al día de la fecha.", HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name());

                            // Si llego aqui creo el Empleado
                        }else{
                            return true;
                         }

    }

    // Creo Empleado
    /**
     *
     * @param emp : recibo los datos de un empleado
     * @return :Empleado creado
     */
    @Override
    public Empleado crearEmpleado(Empleado emp) {

        LocalDate creacion=LocalDate.now();
        if(analizar(emp,creacion)){
            Empleado empleado =new Empleado(emp.getNroDocumento(),emp.getNombre(),emp.getApellido(),emp.getEmail(),emp.getFechaNacimiento(),emp.getFechaIngreso(),creacion);
            return repoEmpleado.save(empleado);
        }else {
            return null;
        }

    }

    // Retorna lista de todos los Empleados
    @Override
    public List<Empleado> obtenerEmpleados() {
        return repoEmpleado.findAll();
    }

    //Retorna Empleado segun id
    /**
     *
     * @param id:Integer  id para buscar si coincide con algun empleado
     * @return Empleado que coincida con id si se encuentra caso contrario se produce excepcion
     */
    @Override
    public Empleado obtenerEmpleadoId(Integer id) {
        Optional<Empleado> empleado=repoEmpleado.findById(id);
        if(empleado.isPresent()){
            return empleado.get();
        }else {
            throw new Excepciones("No se encontró el empleado con Id:"+id, HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.name());
        }

    }

    // Actualizo Empleado
    /**
     *
     * @param id:Integer id de empleado para actualizar informacion
     * @param empleado:EmpleadoDto  informacion que actualiza los atributos de Empleado
     * @return :Empleado empleado que se actualiza los datos
     */
    @Override
    public Empleado actualizarEmpleado(Integer id, EmpleadoDto empleado) {
        Optional<Empleado> empleadoFind=repoEmpleado.findById(id);
        if(empleadoFind.isPresent()){

            // Realizo set con la informacion actualizada de atributos
            Empleado empleadoUpdate=empleado.toEntity();
            empleadoUpdate.setId(id);
            empleadoUpdate.setFechaCreacion(empleadoFind.get().getFechaCreacion());
            // Si con los datos recibidos paso el analizis guardo los cambios
            if(analizar(empleadoUpdate,empleadoFind.get().getFechaCreacion())){
                return repoEmpleado.save(empleadoUpdate);
            }else{
                return null;
            }

        }else{
            throw new Excepciones("No se encontró el empleado con Id:"+id,HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.name());
        }

    }

    // Elimino Empleado
    /**
     *
     * @param id :Integer id de empleado para eliminar
     */
    @Override
    public void eliminarEmpleado(Integer id) {
        Optional<Empleado> empleadoBorrar=repoEmpleado.findById(id);
        if(empleadoBorrar.isPresent()){
            repoEmpleado.delete(empleadoBorrar.get());

        }else{
            throw new Excepciones("No se encontró el empleado con Id:"+id,HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.name());
        }

    }
}
