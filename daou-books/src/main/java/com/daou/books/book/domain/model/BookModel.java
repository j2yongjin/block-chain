package com.daou.books.book.domain.model;

import com.daou.books.book.domain.Book;
import com.daou.books.core.ProcessStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookModel {

    private Long id;

    private String code;

    private String title;

    private String subtitle;

    private String writer;

    private Integer amount;

    private String publisher;

    private Date issueDate;

    private ProcessStatus status;

    private Date createdAt;

    private Date updatedAt;

    public BookModel(Book book) {
        this.id = book.getId();
        this.code = book.getIsbn();
        this.title = book.getTitle();
        this.subtitle = book.getSubtitle();
        this.writer = book.getWriter();
        this.amount = book.getAmount();
        this.publisher = book.getPublisher();
        this.issueDate = book.getIssueDate();
        this.status = book.getStatus();
        this.createdAt = book.getCreatedAt();
        this.updatedAt = book.getUpdatedAt();
    }
}
