import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from "./register/register.component";
import { PaginationComponent } from './common/pagination/pagination.component';
import { ModalComponent } from './common/modal/modal.component';
import { PaginationService } from './common/pagination/pagination.service.component';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    PaginationComponent,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    PaginationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
