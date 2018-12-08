package com.daou.books.core.service;

import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.CreateCompanyModel;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.repository.CompanyRepository;
import com.daou.books.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageModel<CreateCompanyModel> getCompanies(int page, int offset, String direction, String property) {

        Pageable pageable = new PageRequest(page, offset, new Sort(Sort.Direction.fromString(direction), property));
        Page<User> users = userRepository.findByRole(User.UserRole.ADMIN, pageable);

        List<CreateCompanyModel> models = Lists.newArrayList();
        for(User user: users){
            CreateCompanyModel model = new CreateCompanyModel();
            model.setCompanyCode(user.getCompany().getCode());
            model.setCompanyName(user.getCompany().getName());
            model.setAdminId(user.getLoginId());
            model.setAdminName(user.getName());
            models.add(model);
        }
        return new PageModel(models, users);
    }
}
