package com.daou.books.book.service;

import com.daou.books.book.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getBookList();

    List<Book> addBooks(List<Book> books);

    Book updateBook(Book book);
}
