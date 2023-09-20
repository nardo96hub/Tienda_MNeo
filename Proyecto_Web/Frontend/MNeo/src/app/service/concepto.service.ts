import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Concepto } from '../entity/concepto';
@Injectable({
  providedIn: 'root'
})
export class ConceptoService {

  constructor(private http:HttpClient) { }
  url="http://localhost:8080"
  public getConceptos():Observable<Concepto[]>{
    return this.http.get<Concepto[]>(`${this.url}/concepto`)
  }
}
