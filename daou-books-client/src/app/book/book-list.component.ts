import {Component, ViewChild} from '@angular/core';
import {PaginationComponent} from "../common/pagination/pagination.component";
import * as moment from 'moment';

@Component({
  templateUrl: './book-list.component.html'
})

export class AdminBookListComponent {
  @ViewChild(PaginationComponent) paginationComponent: PaginationComponent;

  companyAdmin;

  pageOptions = {
    url:"api/books",
    defaultSorting:[[1, "desc"]],
    params:{apitype:"APPR"},
    columns:[{dataName:"ISBN", thClass:"date", dataCode:"isbn", sortable:true, convertData: function(data){
        return data.isbn ? data.isbn : "-";
      }},{dataName:"제목", thClass:"date", dataCode:"title", sortable:true, convertData: function(data){
        return data.title ? data.title : "-";
      }},
      {dataName:"작가", thClass:"system", dataCode:"writer", sortable:false, convertData: function(data){
          return data.writer ? data.writer : "-";
        }},
      {dataName:"가격", thClass:"system", dataCode:"amount", sortable:true, convertData: function(data){
          return data.amount ? data.amount : "-";
        }},
      {dataName:"출판사", thClass:"name", dataCode:"publisher", sortable:false, convertData: function(data){
          return data.publisher ? data.publisher : "-";
        }},
      {dataName:"출판일", thClass:"state", dataCode:"issueDate", sortable:true, convertData: function(data){
          return data.issueDate ? moment(data.issueDate).format('YYYY-MM-DD') : '-';
        }, clickCallBack: function(pageThis, data){
          //let url = "/alliance/appr/log/detail/"+data.id;
          //pageThis.router.navigate([url]);
      }},
      {dataName:"판매부수", thClass:"state", dataCode:"salesCount", sortable:true, convertData: function(data){
          return data.salesCount ? data.salesCount : "-";
        }},
      {dataName:"등록상태", thClass:"state", dataCode:"status", sortable:false, convertData: function(data){
          return data.status ? data.status : "-";
        }}]
  };

  constructor(
  ) {}

  onSearchEvent(): void {

  }
}
