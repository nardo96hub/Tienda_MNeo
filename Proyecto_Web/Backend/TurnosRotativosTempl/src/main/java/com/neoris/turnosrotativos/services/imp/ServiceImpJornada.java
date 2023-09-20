package com.neoris.turnosrotativos.services.imp;

import com.neoris.turnosrotativos.dto.JornadaDto;
import com.neoris.turnosrotativos.dto.auxiliar.Semana;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.exception.Excepciones;
import com.neoris.turnosrotativos.repositories.RepositoryConcepto;
import com.neoris.turnosrotativos.repositories.RepositoryEmpleado;
import com.neoris.turnosrotativos.repositories.RepositoryJornada;
import com.neoris.turnosrotativos.services.ServiceJornada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceImpJornada implements ServiceJornada {
    @Autowired
    private RepositoryJornada repoJornada;
    @Autowired
    private RepositoryConcepto repoConcepto;
    @Autowired
    private RepositoryEmpleado repoEmpleado;

    // Obtengo lista de jornadas y realiza filtro dependiendo de los valores de nroDocumento y fecha
    /**
     *
     * @param dni: Integer  nroDocumento de un empleado
     * @param fecha:LocalDate fecha de Jornada
     * @return:List<Jornada> Lista de Jornada segun las condiciones
     */
    @Override
    public List<Jornada> obtenerJornadas(Integer dni, LocalDate fecha) {
        if(dni==null && fecha==null){
            return repoJornada.findAll();
        }else if (dni==null && fecha!=null){
            return repoJornada.findAllByFecha(fecha);
        }else if (dni!=null && fecha==null){
            return repoJornada.findAllByNroDocumento(dni);
        }else{
            return repoJornada.findAllCondicion(dni,fecha);
        }

    }

    // Realiza el analasis de excepciones sobre Datos, si no provoca ninguna, invoca luego una funcion private addJornada si cumple las REGLAS NEGOCIO crea la jornada
    /**
     *
     * @param jornada:JornadaDto
     * @return :Jornada  jornada creada si cumple analisis de Datos y ReglasNegocio
     */
    @Override
    public Jornada crearJornada(JornadaDto jornada){
        Optional< Empleado> empleado=repoEmpleado.findById(jornada.getIdEmpleado());
        // ANALISIS DE DATOS

        // Verifico que exista empleado
        if(empleado.isPresent()){
            Optional<Concepto> concepto= repoConcepto.findById(jornada.getIdConcepto());
            // Verifico que exista concepto
            if(concepto.isPresent()){
                //Verifico que nombreConcepto sea ('Turno Normal'  o  'Turno Extra') y ademas que hsTrabajadas exista
                if((concepto.get().getNombre().equalsIgnoreCase("Turno Normal") || concepto.get().getNombre().equalsIgnoreCase("Turno Extra") )){
                    // Si ademas de las condiciones anteriores  hsTrabajadas existe
                    if(jornada.getHorasTrabajadas()!=null){
                        return addJornada(jornada,empleado.get(),concepto.get());
                    }else{
                        throw new Excepciones("'hsTrabajadas’ es obligatorio para el concepto ingresado.",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name());
                    }
                }else if(concepto.get().getNombre().equalsIgnoreCase("Dia Libre") && jornada.getHorasTrabajadas()==null){
                    return addJornada(jornada,empleado.get(),concepto.get());
                }else{
                    throw new Excepciones(" El concepto ingresado no requiere el ingreso de ‘hsTrabajadas",HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.name());
                }

            }else{
                throw new Excepciones("No existe el concepto ingresado.", HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.name());
            }
        }else {
            throw new Excepciones("No existe el empleado ingresado.", HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.name());
        }

    }

    //Funcion privada que realiza las reglas del Negocio si cumple todos los requisitos la crea
    /**
     *
     * @param jDto:JornadaDto datos de la jornada a crear
     * @param e:Empleado  empleado encontrado con el idEmpleado de jDto
     * @param c:Concepto  concepto encontrado con el idConcepto de jDto
     * @return:Jornada jornada creada si se cumplen el analisis de ReglasNegocio
     */
    private Jornada addJornada(JornadaDto jDto,Empleado e,Concepto c){
        if(analisisReglasNegocio(jDto,e,c)){
            return repoJornada.save(jDto.toEntity(e,c));
        }else{
            return null;
        }
    }

    // Realiza el analisis ReglasNegocio si no provoca ninguna excepcion se puede crear la jornada
    /**
     *
     * @param jDto:JornadaDto datos de la jornada a crear
     * @param e:Empleado  empleado encontrado con el idEmpleado de jDto
     * @param c:Concepto  concepto encontrado con el idConcepto de jDto
     * @return:Jornada jornada creada si se cumplen el analisis de ReglasNegocio
     */
    private Boolean analisisReglasNegocio(JornadaDto jDto,Empleado e,Concepto c){

        // Analizo ReglaNegocio 1)
            // Si el nombre de concepto es distinto a 'Dia Libre  entonces es lo mismo a decir: (  jornada de turno normal o extra) y si  horasTrabajadas !e [hsMinimo,hsMaximo]  no cumple ReglaNegocio 1)
        if(jDto.getHorasTrabajadas()!=null){
            if(!c.getNombre().equalsIgnoreCase("Día libre") && (jDto.getHorasTrabajadas()> c.getHsMaximo() || jDto.getHorasTrabajadas()<c.getHsMinimo())) {
                throw new Excepciones("El rango de horas que se puede cargar para este concepto es de " + c.getHsMinimo() + " - " + c.getHsMaximo(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }
        }

        // Analizo las reglas de negocio [2,5]
            // Que los rn sean true significa que se realiza una excepcion
        Boolean rn2,rn3,rn4,rn5;

        List<Jornada> jornadaBusqueda =repoJornada.findAllCondicion(e.getNroDocumento(),jDto.getFecha());
        rn2=false;rn3=false;rn4=false;rn5=false;
        // Si no hay jornadas creadas entonces no analiza ReglasNegocio y directamente se crea la jornada
        if(!jornadaBusqueda.isEmpty()) {
            // Recorre las jornadas
            for (Jornada j : jornadaBusqueda) {
                // Si es dia libre no se agrega -  ReglaNegocio_2
                if (j.getConcepto().equalsIgnoreCase("Dia Libre")) {
                    rn2 = true;
                }
                //Siempre que no sea dia Libre puedo agregar si no pertenece - ReglaNegocio_3
                if (j.getConcepto().equalsIgnoreCase(c.getNombre()) && !rn2) {
                    rn3 = true;
                }
                // Puedo agregar si el concepto: Turno Normal y jornada:Turno extra o viceversa.Ademas la suma de las horas trabajadas <12 - ReglaNegocio_4
                if (((j.getConcepto().equalsIgnoreCase("Turno Normal") && c.getNombre().equalsIgnoreCase("Turno Extra"))
                        || (j.getConcepto().equalsIgnoreCase("Turno Extra") && c.getNombre().equalsIgnoreCase("Turno Normal"))
                    ) && (j.getHsTrabajadas() + jDto.getHorasTrabajadas()) > 12
                   ) {
                    rn4 = true;
                }
                // No se puede agregar Dia Libre si ya se cargo un turno distinto - ReglaNegocio_5
                if (c.getNombre().equalsIgnoreCase("Dia Libre") && !j.getConcepto().equalsIgnoreCase("Dia Libre")) {
                    rn5 = true;
                }
            }

            //Si algun rnX= true entonces se produce excepcion

            //Analizo ReglaNegocio 2)
            if (rn2) {
                throw new Excepciones("El empleado ingresado cuenta con un día libre en esa fecha", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }
            //Analizo ReglaNegocio 3)
            if (rn3) {
                throw new Excepciones("El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }
            //Analizo ReglaNegocio 4)
            if (rn4) {
                throw new Excepciones("El empleado no puede cargar más de 12 horas trabajadas en un dia", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }
            //Analizo ReglaNegocio 5)
            if (rn5) {
                throw new Excepciones("El empleado no puede cargar Dia Libre si cargo un turno previamente para la fecha ingresada", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }
        }

        Semana rangoSemana=new Semana(jDto.getFecha());
        // Retorna las jornadas de la semana del empleado
        jornadaBusqueda=repoJornada.findAllCondicionRangoFecha(e.getNroDocumento(),rangoSemana.getLunes(),rangoSemana.getDomingo());
        if(!jornadaBusqueda.isEmpty()) {

            Integer contNormal, contExtra, contLibre, contHoras;

            contHoras = 0;contExtra = 0;contLibre = 0;contNormal = 0;

            // Analasis ReglaNegocio de 6 a 9
            for (Jornada j : jornadaBusqueda) {
                if(j.getConcepto().equalsIgnoreCase(c.getNombre())){
                    // Si el concepto es distinto Dia Libre
                    if (!j.getConcepto().equalsIgnoreCase("Dia Libre")) {
                        // Sumo las horas trabajadas
                        contHoras += j.getHsTrabajadas();

                        if (j.getConcepto().equalsIgnoreCase("Turno Normal")) {
                            // max cantidad de Turno Normal:5
                            contNormal++;
                        } else {
                            // max cantidad de Turno Extra:3
                            contExtra++;
                        }
                    } else {
                        // max cantidad de Dia Libre:2
                        contLibre++;
                    }
                }
            }
            // Analizo ReglaNegocio 6)
            if (contHoras >= 48) {
                throw new Excepciones("El empleado ingresado supera las 48 horas semanales.", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }

            // Analizo ReglaNegocio 7)
            if (contExtra >= 3) {
                throw new Excepciones("El empleado ingresado ya cuenta con 3 turnos extra esta semana.", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }

            // Analizo ReglaNegocio 8)
            if (contNormal >= 5) {
                throw new Excepciones("El empleado ingresado ya cuenta con 5 turnos normal esta semana.", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }

            // Analizo ReglaNegocio 9)
            if (contLibre >= 2) {
                throw new Excepciones("El empleado no cuenta con más días libres esta semana.", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name());
            }
        }
        return true;
    }
}
