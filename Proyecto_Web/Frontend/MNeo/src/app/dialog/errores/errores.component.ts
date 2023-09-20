import { Component,Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Error } from 'src/app/entity/error';

@Component({
  selector: 'app-errores',
  templateUrl: './errores.component.html',
  styleUrls: ['./errores.component.css']
})
export class ErroresComponent {

  constructor(private dialogRef:MatDialogRef<ErroresComponent>,@Inject(MAT_DIALOG_DATA) public e:Error){}
}
