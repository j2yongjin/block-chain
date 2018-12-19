import {Component, ViewChild} from '@angular/core';
import {PaginationComponent} from "../common/pagination/pagination.component";
import * as moment from 'moment';
import {AuthService} from "../auth.service";
import {CookieService} from "ngx-cookie-service";

@Component({
  templateUrl: './account-list.component.html'
})

export class AccountListComponent {
  @ViewChild(PaginationComponent) paginationComponent: PaginationComponent;

  pageOptions = {
    url:"api/users",
    defaultSorting:[[1, "desc"]],
    params:{},
    columns:[{dataName:"이름", thClass:"date", dataCode:"name", sortable:true, convertData: function(data){
        return data.name ? data.name : "-";
      }},
      {dataName:"아이디", thClass:"state", dataCode:"loginId", sortable:false, convertData: function(data){
          return data.loginId ? data.loginId : '-';
        }, clickCallBack: function(pageThis, data){
          //let url = "/alliance/appr/log/detail/"+data.id;
          //pageThis.router.navigate([url]);
      }
      }]
  };

  constructor(
    private cookieService: CookieService
  ) {
    const userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
    this.pageOptions.params['companyId'] = userInfo.company.id;
  }

  onSearchEvent(): void {

  }
}
