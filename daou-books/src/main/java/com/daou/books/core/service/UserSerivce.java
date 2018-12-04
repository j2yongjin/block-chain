package com.daou.books.core.service;

import com.daou.books.core.domain.User;

public interface UserSerivce {

    User saveSuperAdmin();

    User saveAdmin();

    User saveUser();

}
