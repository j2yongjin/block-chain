package com.daou.books.book.controller;

import com.daou.books.book.domain.Book;
import com.daou.books.book.domain.model.BookModel;
import com.daou.books.book.service.BookService;
import com.daou.books.book.service.DaouBookService;
import com.daou.books.core.domain.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PageModel<BookModel> getBooks(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property) {
        Pageable pageable = new PageRequest(page, offset, new Sort(Sort.Direction.fromString(direction), property));
        return bookService.getBookList(pageable);
    }

    @PostMapping("/api/books")
    @ResponseBody
    public List<BookModel> addBooks(@RequestBody List<Book> books) {
        return bookService.addBooks(books);
    }

    @PostMapping("/api/book")
    @ResponseBody
    public BookModel addBook(@RequestBody Book book) {
        return bookService.addBook(book);
    }

    @PutMapping("/api/book")
    @ResponseBody
    public BookModel updateBook(@RequestBody Book newBook) {
        return bookService.updateBook(newBook);
    }

}
