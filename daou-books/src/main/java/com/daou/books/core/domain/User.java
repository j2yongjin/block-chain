package com.daou.books.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String login_id;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private Company company;

    @Column
    private UserRole role;

}
