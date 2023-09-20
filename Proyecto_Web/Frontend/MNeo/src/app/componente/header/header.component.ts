import { Component, EventEmitter, Output ,OnInit} from '@angular/core';
import { ModoOscuroService } from 'src/app/service/modo-oscuro.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  constructor(private moscuro:ModoOscuroService){}
  
  @Output() oscuro=new EventEmitter<boolean>()
  modo!:boolean
  ngOnInit(): void{
   this.modo=this.moscuro.getModo()
   console.log(this.modo);
   
  }
  enviarM(){
    this.moscuro.setModo()
    console.log(this.moscuro.getModo());
    
    this.oscuro.emit(this.moscuro.getModo())
  }
}
