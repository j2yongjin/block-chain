import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

import { environment } from 'environments/environment';
import { PageModel } from "./PageModel";


const httpOptions ={
  withCredentials : true,
};

@Injectable()
export class PaginationService {

  host: string;

  constructor(private http: HttpClient) {
    this.host = environment.host
  }

  getPageList(requestUrl: string): Observable<any> {
    const url = `${this.host}/${requestUrl}`;

    return this.http.get<PageModel>(url);
  }

}
