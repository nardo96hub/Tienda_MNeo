import { Component } from '@angular/core';

@Component({
  selector: 'app-crear',
  templateUrl: './crear.component.html',
  styleUrls: ['./crear.component.css']
})
export class CrearComponent {
  oscuro!:boolean
  modoOscuro($event:boolean){
    this.oscuro=$event
    
    
  }
}
