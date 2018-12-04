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
    private String loginId;

    @Column
    private String password;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public enum UserRole {
        SUPERADMIN, ADMIN, USER
    }

    public User(Company company, String adminName, String adminId, String adminPw, UserRole superAdmin) {
        this.company = company;
        this.name = adminName;
        this.loginId = adminId;
        this.password = adminPw;
        this.role = superAdmin;
    }

}
