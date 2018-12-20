package com.daou.books.core.service;

import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.UserModel;

public interface LoginSerivce {

    UserModel login(String id, String pw);
}
