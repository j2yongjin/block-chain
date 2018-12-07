package com.daou.books.core.service;

import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import com.daou.books.core.repository.CompanyRepository;
import com.daou.books.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserSerivceImpl implements UserSerivce {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        return userRepository.findByCompany(company);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        return userRepository.findByCompanyAndRole(company, User.UserRole.USER);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAdmins(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        return userRepository.findByCompanyAndRole(company, User.UserRole.ADMIN);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getSuperAdmin(Long companyId) {
        Company company = companyRepository.findOne(companyId);
        return userRepository.findByCompanyAndRole(company, User.UserRole.SUPERADMIN);
    }

    @Override
    @Transactional
    public User addUser(User user, User.UserRole role) {
        user.setRole(role);
        return userRepository.save(user);
    }

}
