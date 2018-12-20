package com.daou.books.core.service;

import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.UserModel;
import com.daou.books.core.repository.CompanyRepository;
import com.daou.books.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginSerivceImpl implements LoginSerivce {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional
    public UserModel login(String id, String pw) {
        User user = userRepository.findByLoginId(id);
        if(null == user) {
            return null;
        }

        if(!user.getPassword().equals(pw)) {
            return null;
        }

        return new UserModel(user);
    }
}
