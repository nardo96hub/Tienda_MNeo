import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './componente/home/home.component';
import { ListarEmpleadoComponent } from './componente/empleado/listar-empleado/listar-empleado.component';
import { CrearComponent } from './componente/empleado/crear/crear.component';
import { GetEmpleadoComponent } from './componente/empleado/get-empleado/get-empleado.component';
import { ListarConceptoComponent } from './componente/concepto/listar-concepto/listar-concepto.component';

const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'listarEmpleado',component:ListarEmpleadoComponent},
  {path:'crearEmpleado',component:CrearComponent},
  {path:'getEmpleado',component:GetEmpleadoComponent},
  {path:'listarConcepto',component:ListarConceptoComponent},
  {path:'**',redirectTo:''},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
