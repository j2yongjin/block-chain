package com.daou.books.core.service;

import com.daou.books.core.domain.User;

public interface LoginSerivce {

    User login(String id, String pw);
}
