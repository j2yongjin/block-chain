package com.daou.books.core.service;

import com.daou.books.core.domain.model.CreateCompanyModel;

public interface CompanyService {

    CreateCompanyModel saveCompany(CreateCompanyModel newCompany);
}