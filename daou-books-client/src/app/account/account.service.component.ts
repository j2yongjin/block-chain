import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from 'environments/environment';
import {CompanyAdmin} from "./CompanyAdmin";
import {Account} from "./Account";

@Injectable()
export class AccountService {

  host: string;

  private createCompanyUrl = '/api/company';
  private createUserUrl = '/api/user';
  private createAdmin ='api/admin'
  private adminLists = 'api/admin/users';

  constructor(private http: HttpClient) {
    this.host = environment.host
  }

  getAdminLists(): Observable<any> {
    const url = `${this.host}/${this.getAdminLists}`;
    return this.http.get<Array<CompanyAdmin>>(url)
  }

  createCompany (account: CompanyAdmin): Observable<any> {
    const url = `${this.host}/${this.createCompanyUrl}`;
    return this.http.post<CompanyAdmin>(url, account);
  }

  createCompanyAdmin (companyAdmin: CompanyAdmin): Observable<any> {
    const url = `${this.host}/${this.createAdmin}`;
    return this.http.post<CompanyAdmin>(url, companyAdmin);
  }

  createUser (account: Account): Observable<any> {
    const url = `${this.host}/${this.createUserUrl}`;
    return this.http.post<Account>(url, account);
  }

}
