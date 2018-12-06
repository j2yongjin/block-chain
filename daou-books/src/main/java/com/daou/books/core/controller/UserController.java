package com.daou.books.core.controller;

import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/api/user")
    public CreateCompanyModel createCompany(@RequestBody CreateCompanyModel model) {
        return model;
    }
}
