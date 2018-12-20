import {Component} from '@angular/core';
import * as moment from 'moment';
import {ActivatedRoute, Router} from "@angular/router";
import {BookService} from "../book/Book.service.component";
import {Book} from "../book/Book";
import {ModalService} from "../common/modal/modal.service";
import {OrderService} from "./order.service.component";
import {OrderBook} from "./OrderBook";
import {CookieService} from "ngx-cookie-service";
import {AuthService} from "../auth.service";

@Component({
  templateUrl: './order-book.component.html'
})

export class OrderBookComponent {

  bookId: string;
  book: Book = new Book();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService,
    private orderService: OrderService,
    private modalService: ModalService,
    private cookieService: CookieService
  ) {
    this.bookId = this.route.snapshot.paramMap.get('bookId');
    bookService.getBook(this.bookId).subscribe(res => this.book = res as Book)
  }

  moveToList(): void {
    console.log("move to list");
    this.router.navigate(['']);
  }

  order(data:any): void {
    if(data.bid == 'confirm') {
      let orderBook = new OrderBook();
      orderBook.book = this.book;
      let userInfo = JSON.parse(this.cookieService.get(AuthService.COOKIE_KEY));
      orderBook.user = userInfo;
      this.orderService.orderBook(orderBook).subscribe();
    }
  }
}
