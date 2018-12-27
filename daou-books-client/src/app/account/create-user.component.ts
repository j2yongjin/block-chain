import {Component} from '@angular/core';
import {AccountService} from "./account.service.component";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../auth.service";
import {Account} from "./Account";
import {ModalService} from "../common/modal/modal.service";
import {Router} from "@angular/router";

@Component({
  selector: 'create-user',
  templateUrl: './create-user.component.html'
})

export class CreateUserComponent {

  account: Account = new Account();
  message = '';

  constructor(
    private router: Router,
    private accountService: AccountService,
    private cookieService: CookieService,
    private modalService: ModalService
  ) {
    const userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
    this.account.company = userInfo.company;
  }

  createUser(): void {
    if (!this.account.loginId || !this.account.name || !this.account.password) {
      this.message = '정보를 모두 입력해주세요.';
      this.modalService.getModal('notiMessage').openNoti();
      return;
    }
    this.accountService.createUser(this.account).subscribe(res => {
      this.account = res as Account;
      /*this.message = '저장되었습니다.';
      this.modalService.getModal('notiMessage').openNoti();*/
      this.router.navigate(['admin/user/list']);
    });

  }
}
