import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from "./auth.service";
import {CookieService} from "ngx-cookie-service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  id: string = '';
  pw: string = '';
  authorized = false;
  userInfo;

  constructor(
    private router: Router,
    private authService: AuthService,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.authorized = this.authService.isAuthenticated();
    if (this.authorized) {
      this.redirect();
    }
  }

  login(): void {
    if (!this.id || !this.pw) {
      alert("아이디와 비밀번호를 입력해주세요.");
      return;
    }
    if (!this.authorized) {
      this.authService.authenticated(this.id, this.pw).subscribe((res) => {
        if (res) {
          this.authService.setSessionUser(res);
          this.authorized = this.authService.isAuthenticated();
          this.redirect();
        } else {
          alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
      }, (error) => {
        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        this.authService.emptyCookie();
      });
    }
  }

  keyboardEvent(event: any): void {
    if (event.key == 'Enter') {
      this.login();
    }
  }

  redirect(): void {
    this.userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
    console.log(this.userInfo);
    if (this.userInfo.role == 'SUPERADMIN') {
      this.router.navigate(['admin/company/list']);
      return;
    } else if (this.userInfo.role == 'ADMIN') {
      this.router.navigate(['admin/user/create']);
      return;
    } else {
      // this.router.navigate([`/alliance/oauth/history`]);
    }
  }

}

