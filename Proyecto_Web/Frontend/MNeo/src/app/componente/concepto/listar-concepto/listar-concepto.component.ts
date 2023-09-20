import { Component ,OnInit} from '@angular/core';
import { Concepto } from 'src/app/entity/concepto';
import { ConceptoService } from 'src/app/service/concepto.service';

export interface ConceptoElemento{
  id:number;
  nombre:string;
  hsMinimo:number;
  hsMaximo:number;
  laborable:boolean;
}

const ELEMENTO:ConceptoElemento[] =[
  {id:1,nombre:"Turno Normal",hsMinimo:6,hsMaximo:8,laborable:true},
  {id:2,nombre:"Turno Extra",hsMinimo:6,hsMaximo:8,laborable:true},
  {id:3,nombre:"Dia Libre",hsMinimo:0,hsMaximo:0,laborable:false},
]
@Component({
  selector: 'app-listar-concepto',
  templateUrl: './listar-concepto.component.html',
  styleUrls: ['./listar-concepto.component.css']
})
export class ListarConceptoComponent implements OnInit {
  constructor(private concepto:ConceptoService){}

  oscuro!:boolean
  modoOscuro($event:boolean){
    this.oscuro=$event
    
    
  }
  tablaConcepto:string[]=['id','nombre','hsMin','hsMax','laborable']
  listaConcepto!:Concepto[]/*=ELEMENTO*/

  
  ngOnInit():void{
    this.concepto.getConceptos().subscribe(c=>{
      
      
      this.listaConcepto=c
     
      console.log(this.listaConcepto);
      
    })
  }


}
