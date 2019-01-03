package com.daou.books.book.domain;

import com.daou.books.book.domain.model.BookModel;
import com.daou.books.core.ProcessStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String isbn;

    @Column
    private String title;

    @Column
    private String subtitle;

    @Column
    private String writer;

    @Column
    private Integer amount;

    @Column
    private Long salesCount;

    @Column
    private String publisher;

    @Column
    private Date issueDate;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    public Book() {
    }

    public Book(BookModel model) {
        this.id = model.getId();
        this.isbn = model.getIsbn();
        this.title = model.getTitle();
        this.subtitle = model.getSubtitle();
        this.writer = model.getWriter();
        this.amount = model.getAmount();
        this.publisher = model.getPublisher();
        this.issueDate = model.getIssueDate();
    }

    @PrePersist
    public void onPrePersist() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }

        if (this.updatedAt == null) {
            this.updatedAt = this.createdAt;
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = new Date();
    }

}
