package com.daou.books.core.service;

import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.domain.model.PageModel;

import java.util.List;

public interface CompanyService {

    CreateCompanyModel addCompany(CreateCompanyModel newCompany);

    PageModel<CreateCompanyModel> getCompanies(int page, int offset, String direction, String property);
}
