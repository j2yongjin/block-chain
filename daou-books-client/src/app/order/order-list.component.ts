import {Component, ViewChild} from '@angular/core';
import {PaginationComponent} from "../common/pagination/pagination.component";
import * as moment from 'moment';
import {AuthService} from "../auth.service";
import {CookieService} from "ngx-cookie-service";

@Component({
  templateUrl: './order-list.component.html'
})

export class OrderListComponent {
  @ViewChild(PaginationComponent) paginationComponent: PaginationComponent;

  pageOptions = {
    url:"api/orders/",
    defaultSorting:[[1, "desc"]],
    params:{},
    columns:[{dataName:"구매일자", thClass:"state", dataCode:"createdAt", sortable:false, convertData: function(data){
        return data.createdAt ? moment(data.createdAt).format('YYYY-MM-DD') : '-';
      }},
      {dataName:"제목", thClass:"date", dataCode:"book.title", sortable:true, convertData: function(data){
        return data.book.title ? data.book.title : "-";
      }},
      {dataName:"작가", thClass:"system", dataCode:"book.writer", sortable:false, convertData: function(data){
          return data.book.writer ? data.book.writer : "-";
        }},
      {dataName:"가격", thClass:"system", dataCode:"book.amount", sortable:false, convertData: function(data){
          return data.book.amount ? data.book.amount : "-";
        }},
      {dataName:"출판사", thClass:"name", dataCode:"book.publisher", sortable:true, convertData: function(data){
          return data.book.publisher ? data.book.publisher : "-";
        }}]
  };

  constructor(
    private cookieService: CookieService
  ) {
    let userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
    this.pageOptions.url = this.pageOptions.url + userInfo.id;
  }

  onSearchEvent(): void {

  }
}
