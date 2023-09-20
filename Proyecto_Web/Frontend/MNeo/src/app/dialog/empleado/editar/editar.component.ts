import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_MOMENT_DATE_ADAPTER_OPTIONS, MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import * as moment from 'moment';

import { EmpleadoDto } from 'src/app/entity/empleado/dto/empleado-dto';
import { Empleado } from 'src/app/entity/empleado/empleado';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css'],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS],
    },
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ],

})
export class EditarComponent implements OnInit {

  editarEmpleado:FormGroup

  constructor(private dialogRef:MatDialogRef<EditarComponent>,@Inject(MAT_DIALOG_DATA) private data:Empleado ,private builder:FormBuilder,private adapter:DateAdapter<any>,@Inject(MAT_DATE_LOCALE) private locale:string,private route:Router) {
  
    this.editarEmpleado =this.builder.group({
      dni:['',Validators.required],
      nombre:['',[Validators.required,Validators.pattern('[a-zA-Z ]*')]],
      apellido:['',[Validators.required,Validators.pattern('[a-zA-Z ]*')]],
      email:['',[Validators.required,Validators.email]],
      fechaNacimiento:['',Validators.required],
      fechaIngreso:['',Validators.required],
    })
  }
  ngOnInit(): void {

    this.editarEmpleado.get('dni')?.setValue(this.data.nroDocumento)
    this.editarEmpleado.get('nombre')?.setValue(this.data.nombre)
    this.editarEmpleado.get('apellido')?.setValue(this.data.apellido)
    this.editarEmpleado.get('email')?.setValue(this.data.email)
    this.formatoFecha('fechaIngreso',this.data.fechaIngreso)
    this.formatoFecha('fechaNacimiento',this.data.fechaNacimiento)
  
    
  }
  //Dado la fecha ingresado en input regresa formato YYYY-MM-DD
  public formato(input:string):string{ 
    var m,d,a
    if(this.editarEmpleado.get(input)?.value){      
      a=moment(this.editarEmpleado.get(input)?.value).year()
      m=moment(this.editarEmpleado.get(input)?.value).month()
      d=moment(this.editarEmpleado.get(input)?.value).date()  
      m++ //Los meses van de [0,11]
      if(m<10){
        m='0'+m
      }
      if(d<10){
        d='0'+d
      }
        return a+'-'+m+'-'+d
    }else return ""
  
   
   }

  // Edito un Empleado
  public editar():void{
    const emp:{dni:number;nom:string;ape:string;email:string;nac:string;ing:string} = {dni:0,nom:"",ape:"",email:"",nac:"",ing:""}
    emp.dni= this.editarEmpleado.get('dni')?.value
    emp.nom=this.editarEmpleado.get('nombre')?.value
    emp.ape=this.editarEmpleado.get('apellido')?.value
    emp.email=this.editarEmpleado.get('email')?.value

    
    emp.nac=this.formato('fechaNacimiento')
    emp.ing=this.formato('fechaIngreso')  
    var empleado:EmpleadoDto=new EmpleadoDto(emp.dni,emp.nom,emp.ape,emp.email,emp.nac,emp.ing)
    this.dialogRef.close(empleado)
  
  }
  // Dada una fecha con formato YYYY-MM-DD la convierto a DD/MM/YYYY y guardo en input
  public formatoFecha(nombre:string,dato:string):void{

    const datoFecha=dato.split("-")
    console.log("Dato: "+dato+" separado:"+datoFecha);
    
    var m,d,a
    a=Number(datoFecha[0])
    m=Number(datoFecha[1])-1 //DatePicker va de [0,11] y cuando cree empleado sume 1 por eso le vuelvo a restar
    d=Number(datoFecha[2])

    var fecha=new Date()
    fecha.setFullYear(a,m,d)
    this.editarEmpleado.get(nombre)?.setValue(fecha)

  }
  //funcion auxiliar para que al dar salir en editar no realice la funcion editar()
  public salir():void{
    this.dialogRef.close(0)
  }
}
