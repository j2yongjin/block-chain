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
            String isbn = generateIsbn();
            book.setIsbn(isbn);
            newBooks.add(new BookModel(bookRepository.save(book)));
        }
        return books;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        String isbn = generateIsbn();
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        return null;
    }

    private String generateIsbn() {
        long timeSeed = System.nanoTime(); // to get the current date time value
        double randSeed = Math.random() * 1000; // random number generation
        long midSeed = (long) (timeSeed * randSeed); // mixing up the time and

        String s = midSeed + "";
        String subStr = s.substring(0, 8);
        int finalSeed = Integer.parseInt(subStr); // integer value

        String num = String.format("%08d", finalSeed);
        String newIbsn = String.format("1000%s", num);
        log.info("new ibsn: {}", newIbsn);

        return newIbsn;
    }
}
