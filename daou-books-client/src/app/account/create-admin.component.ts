import {Component} from '@angular/core';
import {AccountService} from "./account.service.component";
import {CompanyAdmin} from "./CompanyAdmin";
import {Account} from "./Account";
import {ModalService} from "../common/modal/modal.service";
import {Router} from "@angular/router";

@Component({
  selector: 'create-admin',
  templateUrl: './create-admin.component.html'
})

export class CreateAdminComponent {

  company: CompanyAdmin = new CompanyAdmin();
  message = '';

  constructor(
    private router: Router,
    private accountService: AccountService,
    private modalService: ModalService,
  ) {}

  createCompany(): void {
    if (!this.company.adminId || !this.company.adminName || !this.company.adminPw || !this.company.companyCode || !this.company.companyName) {
      this.message = '정보를 모두 입력해주세요.';
      this.modalService.getModal('notiMessage').openNoti();
      return;
    }

    this.accountService.createCompany(this.company).subscribe(res =>{
      this.company = res as CompanyAdmin;
      this.router.navigate(['admin/company/list']);
    });
  }
}
