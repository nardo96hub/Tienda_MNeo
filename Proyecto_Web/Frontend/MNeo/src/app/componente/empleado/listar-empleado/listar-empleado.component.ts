import { Component,ViewChild,AfterViewInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Empleado } from 'src/app/entity/empleado/empleado';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';
import { EmpleadoService } from 'src/app/service/empleado.service';
import { EditarComponent } from 'src/app/dialog/empleado/editar/editar.component';
import { ErroresComponent } from 'src/app/dialog/errores/errores.component';
import { Router } from '@angular/router';

export interface EmpleadoElemento{

}

@Component({
  selector: 'app-listar-empleado',
  templateUrl: './listar-empleado.component.html',
  styleUrls: ['./listar-empleado.component.css']
})
export class ListarEmpleadoComponent implements AfterViewInit{


  @ViewChild('paginator') paginator!:MatPaginator
  @ViewChild(MatSort) matsort!:MatSort
  
  constructor(private dialog:MatDialog,private empleadoS:EmpleadoService,private route:Router){}
  tablaEmpleado!:string[]
  empleados!:Empleado[]
  listaEmpleado!:MatTableDataSource<Empleado>
  listmano!:number[]
  oscuro!:boolean
  modoOscuro($event:boolean){
    this.oscuro=$event
    
    
  }
  ngAfterViewInit():void{
   this.tablaEmpleado=['id','dni','nomYape','email','fechaN','fechaI','fechaC','editar','borrar']      
   this.empleadoS.getEmpleados().subscribe(emps=>{
      this.empleados=emps
      this.listaEmpleado=new MatTableDataSource<Empleado>(this.empleados)
   })
    this.listmano=[5,10]
 }

 // Realiza un filtro a la tabla
  public filtroTabla(evento:Event):void{
    const filtro=(evento.target as HTMLInputElement).value
    this.listaEmpleado.filter=filtro.trim().toLowerCase()
  }

  // Borra un empleado y retorna a pagina principal
  public borrarEmpleado(id:number):void{
    this.empleadoS.borrarEmpleado(id).subscribe(emp=>{
      this.route.navigate([''])
    }
    ,error=>{
      const dialogRef=this.dialog.open(ErroresComponent,{data:error.error})
    })
  }

  // Realiza la edicion de un Empleado
  public openEditEmpleado(empleado:Empleado):void{
   const dialogRef=this.dialog.open(EditarComponent,{data:empleado})
   dialogRef.afterClosed().subscribe(e=>{
     
      // En el caso que de boton Salir en EditarDialog entro aqui por eso cuando salgo por boton Salir envio 0
      if(e!=0){
        this.empleadoS.updateEmpleado(empleado.id,e).subscribe(es=>{
        this.route.navigate([''])
      }
      ,err=>{this.dialog.open(ErroresComponent,{data:err.error})})}
      }
      
   )
  }
}
