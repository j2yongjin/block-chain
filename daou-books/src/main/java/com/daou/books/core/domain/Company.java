package com.daou.books.core.domain;

import com.daou.books.core.domain.model.CompanyModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;


    public Company(CompanyModel company) {
        this.id = company.getId();
        this.code = company.getCode();
        this.name = company.getName();
    }

    public Company(String code, String name) {
        this.code = code;
        this.name = name;
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
