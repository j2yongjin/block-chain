import {Component, OnInit} from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import {AuthService} from "../../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'login-info',
  templateUrl: './login-info.component.html'
})

export class LoginInfoComponent implements OnInit {

  loginId = '';

  constructor(
    private cookieService: CookieService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    let userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
    this.loginId = userInfo.loginId;
  }

  logout(): void {
    this.authService.emptyCookie();
    location.reload();
  }

}
