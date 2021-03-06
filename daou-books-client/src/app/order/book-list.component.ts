import {Component, ViewChild} from '@angular/core';
import {PaginationComponent} from "../common/pagination/pagination.component";
import * as moment from 'moment';

@Component({
  templateUrl: './book-list.component.html'
})

export class BookListComponent {
  @ViewChild(PaginationComponent) paginationComponent: PaginationComponent;

  companyAdmin;

  pageOptions = {
    url:"api/books",
    defaultSorting:[[1, "desc"]],
    params:{apitype:"APPR"},
    columns:[{dataName:"제목", thClass:"date", dataCode:"title", sortable:true, convertData: function(data){
        return data.title ? data.title : "-";
      }},
      {dataName:"작가", thClass:"system", dataCode:"writer", sortable:false, convertData: function(data){
          return data.writer ? data.writer : "-";
        }},
      {dataName:"가격", thClass:"system", dataCode:"amount", sortable:false, convertData: function(data){
          return data.amount ? data.amount : "-";
        }},
      {dataName:"출판사", thClass:"name", dataCode:"publisher", sortable:true, convertData: function(data){
          return data.publisher ? data.publisher : "-";
        }},
      {dataName:"출판일", thClass:"state", dataCode:"issueDate", sortable:false, convertData: function(data){
          return data.issueDate ? moment(data.issueDate).format('YYYY-MM-DD') : '-';
        }},
      {dataName:"구매", thClass:"state", dataCode:"", sortable:false, convertData: function(data){
          return "<a class='btn critical'>구매</a>";
        }, clickCallBack: function(pageThis, data){
          let url = "/book/"+data.id+"/order";
          pageThis.router.navigate([url]);
        }}]
  };

  constructor(
  ) {}

  onSearchEvent(): void {

  }
}
