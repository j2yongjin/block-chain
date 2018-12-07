package com.daou.books.core.controller;

import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/api/company")
    public CreateCompanyModel createCompany(@RequestBody CreateCompanyModel model) {
        return companyService.addCompany(model);
    }
}
