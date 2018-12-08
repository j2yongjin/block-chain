import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from 'environments/environment';
import {Account} from "./Account";

@Injectable()
export class AccountService {

  host: string;

  private createCompanyUrl = '/api/company';
  private createUserUrl = '/api/user';
  private adminLists = 'api/admin/users';

  constructor(private http: HttpClient) {
    this.host = environment.host
  }

  getAdminLists(): Observable<any> {
    const url = `${this.host}/${this.getAdminLists}`;
    return this.http.get<Array<Account>>(url)
  }

  createCompany (account: Account): Observable<any> {
    const url = `${this.host}/${this.createCompanyUrl}`;
    return this.http.post<Account>(url, account);
  }

  createUser (account: Account): Observable<any> {
    const url = `${this.host}/${this.createUserUrl}`;
    return this.http.post<Account>(url, account);
  }

}
