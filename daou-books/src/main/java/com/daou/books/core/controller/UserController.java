package com.daou.books.core.controller;

import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.domain.model.UserModel;
import com.daou.books.core.service.UserSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserSerivce userSerivce;

    @GetMapping("/api/users")
    @ResponseBody
    public PageModel<UserModel> getUsers(
            @RequestParam (value = "companyId", required = true) Long companyId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "20") int offset,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction,
            @RequestParam(value = "property", required = false, defaultValue = "id") String property) {
        Pageable pageable = new PageRequest(page, offset, new Sort(Sort.Direction.fromString(direction), property));
        return userSerivce.getUsers(pageable, companyId);
    }

    @GetMapping("/api/user")
    @ResponseBody
    public UserModel getUser(@PathVariable Long userId) {
        return userSerivce.getUser(userId);
    }

    // 회사 별 일반 유저 생성
    @PostMapping("/api/user")
    public UserModel addUser(@RequestBody UserModel user) {
        return userSerivce.addUser(user, User.UserRole.USER);
    }

    @PostMapping("/api/admin")
    public UserModel addAdmin(@RequestBody UserModel user) {
        return userSerivce.addUser(user, User.UserRole.ADMIN);
    }

    @PostMapping("/api/admin/super")
    public UserModel addSuperAdmin(@RequestBody UserModel user) {
        return userSerivce.addUser(user, User.UserRole.SUPERADMIN);
    }


}
