import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { environment } from 'environments/environment';
import {Book} from "../book/Book";

@Injectable()
export class OrderService {

  host: string;

  private orderBookUrl = '/api/order';

  constructor(private http: HttpClient) {
    this.host = environment.host
  }

  orderBook (orderModel: OrderModel): Observable<any> {
    const url = `${this.host}/${this.orderBookUrl}`;
    return this.http.post<Book>(url, orderModel);
  }


}
