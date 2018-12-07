package com.daou.books.core.controller;

import com.daou.books.core.domain.User;
import com.daou.books.core.service.LoginSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginSerivce loginSerivce;

    @GetMapping(name="/api/login")
    @ResponseBody
    public User login(@PathVariable String id, @PathVariable String pw) {
        return loginSerivce.login(id, pw);
    }
}
