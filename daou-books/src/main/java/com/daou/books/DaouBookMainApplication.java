package com.daou.books;

import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import com.daou.books.core.repository.CompanyRepository;
import com.daou.books.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DaouBookMainApplication implements CommandLineRunner {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DaouBookMainApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        long companyCount = companyRepository.count();
        if(companyCount <= 0) {
            Company company = companyRepository.save(new Company("0001", "저작권협회"));
            User superAdmin = userRepository.save(new User(company, "admin", "admin", "1234", User.UserRole.SUPERADMIN));
        }
    }
}