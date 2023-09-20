import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ModoOscuroService {
  modoOscuro:boolean=false
  constructor() { }
  public getModo(){
    return this.modoOscuro
  }
  public setModo(){
    this.modoOscuro=!this.modoOscuro
  }
}
