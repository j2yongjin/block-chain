package com.daou.books.core.service;

import com.daou.books.core.domain.User;

import java.util.List;

public interface UserSerivce {

    List<User> getAllUsers(Long companyId);

    List<User> getUsers(Long companyId);

    List<User> getAdmins(Long companyId);

    List<User> getSuperAdmin(Long companyId);

    User getUser(Long userId);

    User addUser(User user, User.UserRole role);

//    User addAdmin(User user);

//    User addSuperAdmin(User user);

}
