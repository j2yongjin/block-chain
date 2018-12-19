import {Component} from '@angular/core';
import {Book} from "./Book";
import {bookService} from "./book.service.component";

@Component({
  selector: 'register',
  templateUrl: './register.component.html'
})

export class RegisterComponent {

  book: Book = new Book();

  constructor( private registerService: bookService
  ) {}

  createBook(): void {
    this.registerService.createBook(this.book).subscribe(res => {this.book = res as Book});
  }
}
