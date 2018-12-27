import {Component} from '@angular/core';
import {Book} from "./Book";
import {BookService} from "./Book.service.component";
import {Router} from "@angular/router";
import {ModalService} from "../common/modal/modal.service";
import {CompanyAdmin} from "../account/CompanyAdmin";

@Component({
  selector: 'register',
  templateUrl: './register.component.html'
})

export class RegisterComponent {

  book: Book = new Book();
  message = '';

  constructor(
    private router: Router,
    private registerService: BookService,
    private modalService: ModalService
  ) {}

  createBook(): void {
    if (!this.book.title || !this.book.subtitle || !this.book.writer || !this.book.amount || !this.book.issueDate || !this.book.publisher) {
      this.message = '정보를 모두 입력해주세요.';
      this.modalService.getModal('notiMessage').openNoti();
      return;
    }

    this.registerService.createBook(this.book).subscribe(res => {
      this.book = res as Book;
      this.router.navigate(['admin/book/list']);
    });
  }
}
