package com.daou.books.order.domain;

import com.daou.books.book.domain.Book;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private User user;

    @Column
    private Book book;

    @Column
    private ProcessStatus status;

}
