import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as moment from 'moment';

import { environment } from 'environments/environment';
import {CookieService} from "ngx-cookie-service";
import { Observable } from 'rxjs/Observable';
import {Router} from "@angular/router";

@Injectable()
export class AuthService {
  host:string = "";
  static readonly COOKIE_KEY = '__cookdaoubook__';

  constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {
    this.host = environment.host
  }

  authenticated(id: string, pw: string): Observable<any>{
    const url = `${this.host}/api/login?id=${id}&pw=${pw}`;
    return this.http.get<any>(url);
  }

  setSessionUser(res:any): void{
    this.cookieService.set(AuthService.COOKIE_KEY, JSON.stringify(res), moment().add(2, 'hours').toDate());
  }

  emptyCookie() {
    this.cookieService.delete(AuthService.COOKIE_KEY);
  }

  isAuthenticated(): boolean{
    let sessionUser = this.cookieService.get(AuthService.COOKIE_KEY)
    return !(sessionUser == "" || sessionUser == undefined);
  }

}
