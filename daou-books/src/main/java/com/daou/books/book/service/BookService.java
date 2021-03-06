package com.daou.books.book.service;

import com.daou.books.book.domain.Book;
import com.daou.books.book.domain.model.BookModel;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.model.PageModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    PageModel<BookModel> getBookList(Pageable pageable);

    Book getBook(String isbn);

    Book getBook(Long id);

    List<BookModel> addBooks(List<Book> books);

    BookModel addBook(Book book);

    BookModel updateBook(Book book);

    BookModel updateBookStatus(String isbn, ProcessStatus status);

    void pushQueueForBook(Book book);

    BookModel updateSalesCount(Long orderId, String isbn);
}
