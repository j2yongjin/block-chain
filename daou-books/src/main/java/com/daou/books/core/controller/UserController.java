package com.daou.books.core.controller;

import com.daou.books.core.domain.User;
import com.daou.books.core.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserSerivce userSerivce;

    @GetMapping("/api/users")
    @ResponseBody
    public List<User> getUsers(@PathVariable Long companyId) {
        return userSerivce.getUsers(companyId);
    }

    @GetMapping("/api/user")
    @ResponseBody
    public User getUser(@PathVariable Long userId) {
        return userSerivce.getUser(userId);
    }

    @PostMapping("/api/user")
    public User addUser(@RequestBody User user) {
        return userSerivce.addUser(user, User.UserRole.USER);
    }

    @PostMapping("/api/admin")
    public User addAdmin(@RequestBody User user) {
        return userSerivce.addUser(user, User.UserRole.ADMIN);
    }

    @PostMapping("/api/admin/super")
    public User addSuperAdmin(@RequestBody User user) {
        return userSerivce.addUser(user, User.UserRole.SUPERADMIN);
    }


}
