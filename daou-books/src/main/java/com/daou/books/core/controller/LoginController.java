package com.daou.books.core.controller;

import com.daou.books.core.domain.User;
import com.daou.books.core.service.LoginSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private LoginSerivce loginSerivce;

    @GetMapping("/api/login")
    @ResponseBody
    public User login(@RequestParam(required = false) String id, @RequestParam(required = false) String pw) {
        return loginSerivce.login(id, pw);
    }
}
