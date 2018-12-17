import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from 'environments/environment';
import {Book} from "./Book";

@Injectable()
export class RegisterService {

  host: string;

  private createBookUrl = '/api/book';

  constructor(private http: HttpClient) {
    this.host = environment.host
  }

  // getAdminLists(): Observable<any> {
  //   const url = `${this.host}/${this.getAdminLists}`;
  //   return this.http.get<Array<CompanyAdmin>>(url)
  // }

  createBook (book: Book): Observable<any> {
    const url = `${this.host}/${this.createBookUrl}`;
    return this.http.post<Book>(url, book);
  }

}
