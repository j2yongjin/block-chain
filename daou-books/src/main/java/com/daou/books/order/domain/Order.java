package com.daou.books.order.domain;

import com.daou.books.book.domain.Book;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

    @Column
    private ProcessStatus status;

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
