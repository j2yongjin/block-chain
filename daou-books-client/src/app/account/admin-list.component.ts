import {Component, ViewChild} from '@angular/core';
import {AccountService} from "./account.service.component";
import {CompanyAdmin} from "./CompanyAdmin";
import {PaginationComponent} from "../common/pagination/pagination.component";
import * as moment from 'moment';

@Component({
  templateUrl: './admin-list.component.html'
})

export class AdminListComponent {
  @ViewChild(PaginationComponent) paginationComponent: PaginationComponent;

  companyAdmin;

  pageOptions = {
    url:"api/companies",
    defaultSorting:[[1, "desc"]],
    params:{apitype:"APPR"},
    columns:[{dataName:"회사이름", thClass:"date", dataCode:"companyName", sortable:true, convertData: function(data){
        return data.companyName ? data.companyName : "-";
      }},
      {dataName:"회사코드", thClass:"system", dataCode:"companyCode", sortable:false, convertData: function(data){
          return data.companyCode ? data.companyCode : "-";
        }},
      {dataName:"어드민이름", thClass:"system", dataCode:"adminName", sortable:false, convertData: function(data){
          return data.adminName ? data.adminName : "-";
        }},
      {dataName:"어드민아이디", thClass:"name", dataCode:"adminId", sortable:true, convertData: function(data){
          return data.adminId ? data.adminId : "-";
        }},
      {dataName:"처리상태", thClass:"state", dataCode:"status", sortable:false, convertData: function(data){
          return "<a class='btn minor'>상세보기</a>";
        }, clickCallBack: function(pageThis, data){
          let url = "/alliance/appr/log/detail/"+data.id;
          pageThis.router.navigate([url]);}}]
  };

  constructor(
    private accountService: AccountService
  ) {}

  createCompany(): void {
    this.accountService.createCompany(this.companyAdmin).subscribe(res =>{this.companyAdmin = res as CompanyAdmin});
  }

  onSearchEvent(): void {

  }
}
