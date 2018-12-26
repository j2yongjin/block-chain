package com.daou.books.core.service;

import com.daou.books.core.domain.model.CompanyModel;
import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.domain.model.PageModel;

public interface CompanyService {

    PageModel<CreateCompanyModel> getCompanies(int page, int offset, String direction, String property);

    CreateCompanyModel addCompany(CreateCompanyModel newCompany);

    CompanyModel updateCompany(CompanyModel model);
}
