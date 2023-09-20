import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empleado } from '../entity/empleado/empleado';
import { EmpleadoDto } from '../entity/empleado/dto/empleado-dto';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {

  constructor(private http:HttpClient) { }
  url="http://localhost:8080"

  public crearEmpleado(empleado:EmpleadoDto):Observable<Empleado>{
    return this.http.post<Empleado>(`${this.url}/empleado`,empleado)
  }
  public getEmpleados():Observable<Empleado[]>{
    return this.http.get<Empleado[]>(`${this.url}/empleado`)
  }
  public getEmpleado(id:number):Observable<Empleado>{
    return this.http.get<Empleado>(`${this.url}/empleado/${id}`)
  }

  public updateEmpleado(id:number,empleado:EmpleadoDto):Observable<Empleado>{
    return this.http.put<Empleado>(`${this.url}/empleado/${id}`,empleado)
  }
  public borrarEmpleado(id:number):Observable<any>{
    return this.http.delete<any>(`${this.url}/empleado/${id}`)
  }
}
