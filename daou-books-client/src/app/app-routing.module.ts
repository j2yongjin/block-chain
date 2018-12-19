import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RegisterComponent} from "./book/register.component";
import {CreateAdminComponent} from "./account/create-admin.component";
import {CreateUserComponent} from "./account/create-user.component";
import {AdminListComponent} from "./account/admin-list.component";
import {BookListComponent} from "./book/book-list.component";

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'book', children : [
      { path : 'register', component: RegisterComponent },
      { path : 'list', component: BookListComponent }
    ]},
  { path: 'admin', children : [
      { path : 'user/create', component: CreateUserComponent },
      { path : 'company/create', component: CreateAdminComponent },
      { path : 'company/list', component: AdminListComponent }

    ]}
];

@NgModule({
  imports: [ RouterModule.forRoot(routes,{onSameUrlNavigation: 'reload'})], //https://medium.com/engineering-on-the-incline/reloading-current-route-on-click-angular-5-1a1bfc740ab2
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
