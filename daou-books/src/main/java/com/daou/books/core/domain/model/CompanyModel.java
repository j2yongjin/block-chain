package com.daou.books.core.domain.model;

import com.daou.books.core.domain.Company;

public class CompanyModel {

    private Long id;

    private String code;

    private String name;

    public CompanyModel(Company company) {
        this.id = company.getId();
        this.code = company.getCode();
        this.name = company.getName();
    }
}
