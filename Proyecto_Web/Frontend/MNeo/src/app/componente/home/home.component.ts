import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  oscuro!:boolean
  modoOscuro($event:boolean){
    this.oscuro=$event
    
    
  }
}
