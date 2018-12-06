import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from "./register/register.component";
import { PaginationComponent } from './common/pagination/pagination.component';
import { ModalComponent } from './common/modal/modal.component';
import { PaginationService } from './common/pagination/pagination.service.component';
import { CreateAdminComponent } from "./account/create-admin.component";
import { CreateUserComponent } from "./account/create-user.component";
import { AccountService } from "./account/account.service.component";


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    PaginationComponent,
    ModalComponent,
    CreateAdminComponent,
    CreateUserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    HttpClientXsrfModule
  ],
  providers: [
    PaginationService,
    AccountService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
