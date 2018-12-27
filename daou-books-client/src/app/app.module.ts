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
import {AuthService} from "./auth.service";
import { CookieService } from 'ngx-cookie-service';
import {AdminListComponent} from "./account/admin-list.component";
import {BookService} from "./book/Book.service.component";
import {AdminBookListComponent} from "./book/book-list.component";
import {AccountListComponent} from "./account/account-list.component";
import {BookListComponent} from "./order/book-list.component";
import {OrderBookComponent} from "./order/order-book.component";
import {OrderService} from "./order/order.service.component";
import {ModalService} from "./common/modal/modal.service";
import {OrderListComponent} from "./order/order-list.component";
import {LoginInfoComponent} from "./common/logininfo/login-info.component";


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    PaginationComponent,
    ModalComponent,
    CreateAdminComponent,
    CreateUserComponent,
    AdminListComponent,
    AdminBookListComponent,
    BookListComponent,
    AccountListComponent,
    OrderBookComponent,
    OrderListComponent,
    LoginInfoComponent
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
    BookService,
    OrderService,
    ModalService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
