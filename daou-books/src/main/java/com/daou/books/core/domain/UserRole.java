package com.daou.books.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "userRoles")
@Getter
@Setter
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role ;

    @Column
    private String explain;

    public enum Role{
        SUPERADMIN, ADMIN, USER
    }

}
