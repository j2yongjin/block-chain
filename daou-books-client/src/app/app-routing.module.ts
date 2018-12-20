import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./book/register.component";
import {CreateAdminComponent} from "./account/create-admin.component";
import {CreateUserComponent} from "./account/create-user.component";
import {AdminListComponent} from "./account/admin-list.component";
import {AccountListComponent} from "./account/account-list.component";
import {BookListComponent} from "./order/book-list.component";
import {OrderBookComponent} from "./order/order-book.component";
import {OrderListComponent} from "./order/order-list.component";
import {AdminBookListComponent} from "./book/book-list.component";

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'book', children : [
      { path : 'register', component: RegisterComponent },
      { path : 'list', component: BookListComponent },
      { path : ':bookId/order', component: OrderBookComponent },
      { path : 'order/list', component: OrderListComponent }
    ]},
  { path: 'admin', children : [
      { path : 'user/create', component: CreateUserComponent },
      { path : 'user/list', component: AccountListComponent },
      { path : 'company/create', component: CreateAdminComponent },
      { path : 'company/list', component: AdminListComponent },
      { path : 'book/list', component: AdminBookListComponent }

    ]}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{onSameUrlNavigation: 'reload'})], //https://medium.com/engineering-on-the-incline/reloading-current-route-on-click-angular-5-1a1bfc740ab2
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
