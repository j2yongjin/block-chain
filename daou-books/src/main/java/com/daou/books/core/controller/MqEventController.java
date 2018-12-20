package com.daou.books.core.controller;

import com.daou.books.book.service.BookService;
import com.daou.books.core.ProcessStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MqEventController {

    @Autowired
    private BookService bookService;

    // 책 등록 완료
    @PutMapping("/api/event/book/add")
    public ResponseEntity addBookCompletedEvent(@RequestParam(required = false) String isbn,
                                                @RequestParam(required = false) ProcessStatus status) {
        // logic
        log.info("add ibsn: {}, status: {}", isbn, status);

        bookService.updateBookStatus(isbn, status);

        return new ResponseEntity(isbn, HttpStatus.OK);
    }

    // 판매부수 업데이트 완료
    @PutMapping("/api/event/order/update")
    public ResponseEntity updateBookCompletedEvent(@RequestParam(required = false) String isbn,
                                                   @RequestParam(required = false) Long salesCount) {
        // logic
        log.info("uodate ibsn: {}, salesCount: {}", isbn, salesCount);

        bookService.updateSalesCount(isbn);

        return new ResponseEntity(isbn, HttpStatus.OK);
    }

}
