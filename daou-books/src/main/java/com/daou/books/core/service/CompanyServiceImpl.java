package com.daou.books.core.service;

import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.repository.CompanyRepository;
import com.daou.books.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CreateCompanyModel addCompany(CreateCompanyModel model) {
//        CreateCompanyModel model = new CreateCompanyModel();

        // 1. db save
        Company company = companyRepository.save(new Company(model.getCompanyCode(), model.getCompanyName()));
        User superAdmin = userRepository.save(new User(company, model.getAdminName(), model.getAdminId(), model.getAdminPw(), User.UserRole.ADMIN));

        // 2. chaincode create

        return model;
    }

}
