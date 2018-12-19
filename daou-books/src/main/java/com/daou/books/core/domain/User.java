package com.daou.books.core.domain;

import com.daou.books.core.domain.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
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
    @JsonIgnore
    private Company company;

    @OneToOne(fetch = FetchType.LAZY)
    private BlockchainInfo blockchainInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

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

    public User(Company company, UserModel model) {
        this.company = company;
        this.name = model.getName();
        this.loginId = model.getLoginId();
        this.password = model.getPassword();
        this.role = model.getRole();
    }


    public User(UserModel model) {
        this.company = new Company(model.getCompany());
        this.name = model.getName();
        this.loginId = model.getLoginId();
        this.password = model.getPassword();
        this.role = model.getRole();
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
