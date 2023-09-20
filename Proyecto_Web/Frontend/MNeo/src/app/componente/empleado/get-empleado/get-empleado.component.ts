import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ErroresComponent } from 'src/app/dialog/errores/errores.component';
import { Empleado } from 'src/app/entity/empleado/empleado';
import { EmpleadoService } from 'src/app/service/empleado.service';

@Component({
  selector: 'app-get-empleado',
  templateUrl: './get-empleado.component.html',
  styleUrls: ['./get-empleado.component.css']
})
export class GetEmpleadoComponent {
  id=new FormControl('',[Validators.required])
  empleado!:Empleado
  
  constructor(private empleadoS:EmpleadoService,private dialog:MatDialog){}
  oscuro!:boolean
  modoOscuro($event:boolean){
    this.oscuro=$event
      
  }
  // Realiza la busqueda de un empleado segun el id
  public buscarID():void{    
    this.empleadoS.getEmpleado(Number(this.id.value)).subscribe(e=>{
      this.empleado=e
    }
    ,er=>{
      this.dialog.open(ErroresComponent,{data:er.error})      
    })  
  }
}
