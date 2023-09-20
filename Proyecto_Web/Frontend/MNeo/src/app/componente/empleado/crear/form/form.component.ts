
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpleadoDto } from 'src/app/entity/empleado/dto/empleado-dto';
import{MAT_MOMENT_DATE_FORMATS,MomentDateAdapter,MAT_MOMENT_DATE_ADAPTER_OPTIONS}from '@angular/material-moment-adapter'
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import * as moment from 'moment';
import { EmpleadoService } from 'src/app/service/empleado.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ErroresComponent } from 'src/app/dialog/errores/errores.component';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css'],
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
export class FormComponent {
  crearEmpleado:FormGroup 
  disable:boolean=true
  constructor(private empleadoS:EmpleadoService,private builder:FormBuilder, private router: Router,private dialog:MatDialog/*,private adapter:DateAdapter<any>/*,@Inject(MAT_DATE_LOCALE) private locale:string*/) {
  
    this.crearEmpleado =this.builder.group({
      dni:['',Validators.required],
      nombre:['',[Validators.required,Validators.pattern('[a-zA-Z ]*')]],
      apellido:['',[Validators.required,Validators.pattern('[a-zA-Z ]*')]],
      email:['',[Validators.required,Validators.email]],
      fechaNacimiento:['',Validators.required],
      fechaIngreso:['',Validators.required],
    })
  }

  //Dado la fecha ingresado en input regresa formato YYYY-MM-DD
  public formato(input:string):string{ 
    var m,d,a
    if(this.crearEmpleado.get(input)?.value){      
      a=moment(this.crearEmpleado.get(input)?.value).year()
      m=moment(this.crearEmpleado.get(input)?.value).month()
      d=moment(this.crearEmpleado.get(input)?.value).date()  
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
 // Creo empleado
  public crear():void{
   
    
    const emp:{dni:number;nom:string;ape:string;email:string;nac:string;ing:string} = {dni:0,nom:"",ape:"",email:"",nac:"",ing:""}
   
    emp.dni= this.crearEmpleado.get('dni')?.value
    emp.nom=this.crearEmpleado.get('nombre')?.value
    emp.ape=this.crearEmpleado.get('apellido')?.value
    emp.email=this.crearEmpleado.get('email')?.value
   
    emp.nac=this.formato('fechaNacimiento')
    emp.ing=this.formato('fechaIngreso')  
    var empleado:EmpleadoDto=new EmpleadoDto(emp.dni,emp.nom,emp.ape,emp.email,emp.nac,emp.ing)
    this.empleadoS.crearEmpleado(empleado).subscribe(e=>{
      this.router.navigate(['listarEmpleado'])
    }
    , error=>{      
      const dialogRef=this.dialog.open(ErroresComponent,{data:error.error})
    }
    )

  }

  // Seteo vacio en los input
  public limpiar():void{
    this.crearEmpleado.get('dni')?.setValue('')
    this.crearEmpleado.get('nombre')?.setValue('')
    this.crearEmpleado.get('apellido')?.setValue('')
    this.crearEmpleado.get('email')?.setValue('')
    this.crearEmpleado.get('fechaNacimiento')?.setValue('')
    this.crearEmpleado.get('fechaIngreso')?.setValue('')
     
  }
}
