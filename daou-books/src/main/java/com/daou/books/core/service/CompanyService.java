package com.daou.books.core.service;

import com.daou.books.core.domain.model.CreateCompanyModel;

import java.util.List;

public interface CompanyService {

    CreateCompanyModel addCompany(CreateCompanyModel newCompany);

    List<CreateCompanyModel> getCompanies();
}
