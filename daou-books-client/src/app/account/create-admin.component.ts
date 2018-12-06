import {Component} from '@angular/core';
import {AccountService} from "./account.service.component";
import {Account} from "./Account";

@Component({
  selector: 'create-admin',
  templateUrl: './create-admin.component.html'
})

export class CreateAdminComponent {

  account: Account = new Account();

  constructor(
    private accountService: AccountService
  ) {}

  createCompany(): void {
    this.accountService.createCompany(this.account).subscribe(res =>{this.account = res as Account});
  }
}
