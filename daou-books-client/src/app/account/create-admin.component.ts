import {Component} from '@angular/core';
import {AccountService} from "./account.service.component";
import {CompanyAdmin} from "./CompanyAdmin";

@Component({
  selector: 'create-admin',
  templateUrl: './create-admin.component.html'
})

export class CreateAdminComponent {

  company: CompanyAdmin = new CompanyAdmin();

  constructor(
    private accountService: AccountService
  ) {}

  createCompany(): void {
    this.accountService.createCompany(this.company).subscribe(res =>{this.company = res as CompanyAdmin});
  }
}
