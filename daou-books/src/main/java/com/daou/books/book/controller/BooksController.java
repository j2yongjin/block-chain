package com.daou.books.book.controller;

import com.daou.books.book.domain.Book;
import com.daou.books.book.service.BookService;
import com.daou.books.book.service.DaouBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {

    @Autowired
    private DaouBookService daouBookService;

    @Autowired
    private BookService bookService;


    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        daouBookService.getBookList();
        return "hello";
    }

    @GetMapping("/api/books")
    @ResponseBody
    public List<Book> getBooks() {
        return bookService.getBookList();
    }

    @PostMapping("/api/books")
    @ResponseBody
    public List<Book> addBooks(@RequestBody List<Book> books) {
        return bookService.addBooks(books);
    }

    @PutMapping("/api/book")
    @ResponseBody
    public Book updateBook(@RequestBody Book newBook) {
        return bookService.updateBook(newBook);
    }

}
