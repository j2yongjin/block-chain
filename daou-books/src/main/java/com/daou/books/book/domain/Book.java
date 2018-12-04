package com.daou.books.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String code;

    @Column
    private String title;

    @Column
    private String subtitle;

    @Column
    private String writer;

    @Column
    private Integer amount;

    @Column
    private String publisher;

    @Column
    private Date issueDate;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
