import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from "./book/register.component";
import { PaginationComponent } from './common/pagination/pagination.component';
import { ModalComponent } from './common/modal/modal.component';
import { PaginationService } from './common/pagination/pagination.service.component';
import { CreateAdminComponent } from "./account/create-admin.component";
import { CreateUserComponent } from "./account/create-user.component";
import { AccountService } from "./account/account.service.component";
import {LayoutComponent} from "./common/layout/layout.component";
import {AuthService} from "./auth.service";
import { CookieService } from 'ngx-cookie-service';
import {AdminListComponent} from "./account/admin-list.component";
import {bookService} from "./book/book.service.component";
import {BookListComponent} from "./book/book-list.component";


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    PaginationComponent,
    ModalComponent,
    CreateAdminComponent,
    CreateUserComponent,
    LayoutComponent,
    AdminListComponent,
    BookListComponent
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
    AccountService,
    AuthService,
    CookieService,
    bookService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
