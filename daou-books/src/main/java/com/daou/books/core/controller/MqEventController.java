package com.daou.books.core.controller;

import com.daou.books.book.service.BookService;
import com.daou.books.core.ProcessStatus;
import com.daou.books.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MqEventController {

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    // 책 등록 완료
    @PutMapping("/api/event/book/add/{isbn}")
    public ResponseEntity addBookCompletedEvent(@PathVariable(required = false) String isbn) {
        // logic
        log.info("add ibsn: {}, status: {}", isbn, ProcessStatus.COMPLETED);

        bookService.updateBookStatus(isbn, ProcessStatus.COMPLETED);

        return new ResponseEntity(isbn, HttpStatus.OK);
    }

    // 판매부수 업데이트 완료
    @PutMapping("/api/event/order/{orderId}/update/{isbn}")
    public ResponseEntity updateBookCompletedEvent(@PathVariable(required = false) Long orderId,
                                                   @PathVariable(required = false) String isbn) {
        // logic
        log.info("uodate ibsn: {}, orderId: {}", isbn, orderId);

        //  book salescount update
        bookService.updateSalesCount(orderId, isbn);

        // order update
        orderService.updateOrderStatus(orderId, ProcessStatus.COMPLETED);

        return new ResponseEntity(isbn, HttpStatus.OK);
    }

}
