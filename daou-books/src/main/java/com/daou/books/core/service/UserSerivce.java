package com.daou.books.core.service;

import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.domain.model.UserModel;
import org.springframework.data.domain.Pageable;

public interface UserSerivce {

    PageModel<UserModel> getAllUsers(Pageable pageable, Long companyId);

    PageModel<UserModel> getAllAdmins(Pageable pageable);

    PageModel<UserModel> getUsers(Pageable pageable, Long companyId);

    PageModel<UserModel> getAdmins(Pageable pageable, Long companyId);

    User getUser(Long userId);

    UserModel addUser(UserModel model, User.UserRole role);

//    UserModel addSuperAdmin(User user);

}
