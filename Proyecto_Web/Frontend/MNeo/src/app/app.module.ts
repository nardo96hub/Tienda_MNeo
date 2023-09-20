import { NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

//Componentes
import { AppComponent } from './app.component';
import { HomeComponent } from './componente/home/home.component';
import { FooterComponent } from './componente/footer/footer.component';
import { HeaderComponent } from './componente/header/header.component';
import { CrearComponent } from './componente/empleado/crear/crear.component';
import { ListarEmpleadoComponent } from './componente/empleado/listar-empleado/listar-empleado.component';
import { ListarConceptoComponent } from './componente/concepto/listar-concepto/listar-concepto.component';
import { GetEmpleadoComponent } from './componente/empleado/get-empleado/get-empleado.component';
import { FormComponent } from './componente/empleado/crear/form/form.component';
import { EditarComponent } from './dialog/empleado/editar/editar.component';
import { ErroresComponent } from './dialog/errores/errores.component';
import { ContenidoComponent } from './componente/home/contenido/contenido.component';

//MaterialAngular
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule,MAT_DATE_LOCALE ,DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import {MatTableModule} from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import {MatPaginatorModule } from '@angular/material/paginator';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatTabsModule} from '@angular/material/tabs';
import {MatListModule} from '@angular/material/list';



const componentes=[ HomeComponent,FooterComponent,HeaderComponent,CrearComponent,
                    ListarEmpleadoComponent,ListarConceptoComponent,GetEmpleadoComponent,FormComponent,                                     
                  ]

const dialog=[EditarComponent,ErroresComponent ]

const angularMaterial=[ MatButtonModule,MatToolbarModule,MatMenuModule,MatIconModule,
                        MatInputModule,MatFormFieldModule,MatDatepickerModule,MatNativeDateModule,
                        MatDatepickerModule, MatTableModule,MatPaginatorModule,MatDialogModule,
                        MatSortModule,MatSlideToggleModule,MatTabsModule,MatListModule
                      ]
@NgModule({
  declarations: [
    AppComponent,
    componentes,
    dialog,
    ContenidoComponent,

    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    
    angularMaterial
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {

}
