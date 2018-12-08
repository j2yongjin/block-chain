package com.daou.books.book.service;

import com.daou.books.book.domain.Book;
import com.daou.books.book.domain.model.BookModel;
import com.daou.books.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getBookList() {
        List<Book> books = bookRepository.findAll();
        List<BookModel> newBooks = Lists.newArrayList();

        for(Book book : books) {
            newBooks.add(new BookModel(book));
        }
        return books;
    }

    @Override
    @Transactional
    public List<Book> addBooks(List<Book> books) {
        List<BookModel> newBooks = Lists.newArrayList();

        for(Book book : books) {
            newBooks.add(new BookModel(bookRepository.save(book)));
        }
        return books;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        return null;
    }

}
