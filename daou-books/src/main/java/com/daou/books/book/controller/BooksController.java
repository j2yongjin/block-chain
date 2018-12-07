package com.daou.books.book.controller;

import com.daou.books.book.domain.Book;
import com.daou.books.book.service.BookService;
import com.daou.books.book.service.DaouBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BooksController {

    @Autowired
    private DaouBookService daouBookService;

    @Autowired
    private BookService bookService;


//    @GetMapping(name = "/hello")
//    @ResponseBody
//    public String hello() {
//        daouBookService.getBookList();
//        return "hello";
//    }

    @GetMapping(name="/api/books")
    @ResponseBody
    public List<Book> getBooks() {
        return bookService.getBookList();
    }

    @PostMapping(name = "/books")
    @ResponseBody
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return bookService.addBooks(books);
    }

    @PutMapping(name = "/book")
    @ResponseBody
    public Book updateBook(@RequestBody Book newBook) {
        return bookService.updateBook(newBook);
    }

}
