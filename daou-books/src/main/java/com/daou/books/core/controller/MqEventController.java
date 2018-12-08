package com.daou.books.core.controller;

import com.daou.books.book.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MqEventController {

    @PutMapping("/api/event/book/add")
    public ResponseEntity addBookCompletedEvent(@RequestBody Book book) {

        // logic
        log.info("add ibsn: {},  status: {}", book.getIsbn(), book.getStatus());

        return new ResponseEntity(book.getIsbn(), HttpStatus.OK);
    }

    @PutMapping("/api/event/book/update")
    public ResponseEntity updateBookCompletedEvent(@RequestBody Book book) {

        // logic
        log.info("uodate ibsn: {},  status: {}", book.getIsbn(), book.getStatus());

        return new ResponseEntity(book.getIsbn(), HttpStatus.OK);
    }

}
