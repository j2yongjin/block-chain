package com.daou.supplier.service;

import com.daou.supplier.model.User;

/**
 * Created by yjlee on 2018-10-28.
 */
public interface UserService {

    public User getAdminUser();
    public User getUser(String id);
    public User registerNewUser(User user ,     String id);


}
