import {Component} from '@angular/core';
import * as moment from 'moment';
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../book/Book.service.component";
import {Book} from "../book/Book";

@Component({
  templateUrl: './order-book.component.html'
})

export class OrderBookComponent {

  bookId: string;
  book: Book = new Book();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService
  ) {
    this.bookId = this.route.snapshot.paramMap.get('bookId').toUpperCase();
    bookService.getBook(this.bookId).subscribe(res => this.book = res as Book)
  }

  moveToList(): void {
    console.log("move to list");
    this.router.navigate(['']);
  }

  orderBook(): void {
    console.log("order order");
  }
}
