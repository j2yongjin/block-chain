package com.daou.books.core.controller;

import com.daou.books.core.domain.model.CompanyModel;
import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/api/company")
    public CreateCompanyModel createCompany(@RequestBody CreateCompanyModel model) {
        return companyService.addCompany(model);
    }

    @GetMapping("/api/companies")
    public PageModel<CreateCompanyModel> getCompanies(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property) {
        return companyService.getCompanies(page, offset, direction, property);
    }

    @PutMapping("/api/company")
    public CompanyModel updateCompany(@RequestBody CompanyModel model) {
        return companyService.updateCompany(model);
    }
}
