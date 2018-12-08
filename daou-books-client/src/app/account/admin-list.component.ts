import {Component, ViewChild} from '@angular/core';
import {AccountService} from "./account.service.component";
import {Account} from "./Account";
import {PaginationComponent} from "../common/pagination/pagination.component";
import * as moment from 'moment';

@Component({
  templateUrl: './admin-list.component.html'
})

export class AdminListComponent {
  @ViewChild(PaginationComponent) paginationComponent: PaginationComponent;

  pageOptions = {
    url:"api/companies",
    defaultSorting:[[1, "desc"]],
    params:{apitype:"APPR"},
    columns:[{dataName:"회사이름", thClass:"date", dataCode:"companyName", sortable:true, convertData: function(data){
        return moment(data.createdAt).format("YYYY-MM-DD hh:mm");
      }},
      {dataName:"회사코드", thClass:"system", dataCode:"companyCode", sortable:false, convertData: function(data){
          return data.partnerName ? data.partnerName : "-";
        }},
      {dataName:"어드민이름", thClass:"system", dataCode:"adminName", sortable:false, convertData: function(data){
          return data.formName ? data.formName : "-";
        }},
      {dataName:"어드민아이디", thClass:"name", dataCode:"adinmPw", sortable:true, convertData: function(data){
          return data.actor ? data.actor : "-";
        }},
      {dataName:"처리상태", thClass:"state", dataCode:"status", sortable:false, convertData: function(data){
          if(data.status == "SUCCESS"){
            return "<a class='btn minor'>정상</a>";
          }else{
            return "<a class='btn critical'>실패</a>";
          }
        }, clickCallBack: function(pageThis, data){
          let url = "/alliance/appr/log/detail/"+data.id;
          pageThis.router.navigate([url]);}}]
  };

  constructor(
    private accountService: AccountService
  ) {}

  createCompany(): void {
    this.accountService.createCompany(this.account).subscribe(res =>{this.account = res as Account});
  }
}
