package com.daou.books.order.domain;

import com.daou.books.book.domain.Book;
import com.daou.books.core.ProcessStatus;
import com.daou.books.core.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> book;

    @Column
    private ProcessStatus status;

}
