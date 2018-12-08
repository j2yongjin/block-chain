import {Component} from '@angular/core';
import {Account} from "./Account";
import {AccountService} from "./account.service.component";

@Component({
  selector: 'create-user',
  templateUrl: './create-user.component.html'
})

export class CreateUserComponent {

  account: Account = new Account();

  constructor(
    private accountService: AccountService
  ) {}

  createUser(): void {
    this.accountService.createUser(this.account).subscribe(res => {this.account = res as Account});
  }
}
