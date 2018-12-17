import {Component} from '@angular/core';
import {AccountService} from "./account.service.component";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../auth.service";
import {Account} from "./Account";

@Component({
  selector: 'create-user',
  templateUrl: './create-user.component.html'
})

export class CreateUserComponent {

  account: Account = new Account();

  constructor(
    private accountService: AccountService,
    private cookieService: CookieService
  ) {
    const userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
    this.account.company = userInfo.company;
  }

  createUser(): void {
    this.accountService.createUser(this.account).subscribe(res => {this.account = res as Account});
  }
}
