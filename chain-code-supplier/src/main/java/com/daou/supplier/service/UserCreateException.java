package com.daou.supplier.service;

/**
 * Created by yjlee on 2018-11-18.
 */
public class UserCreateException extends RuntimeException {
    public UserCreateException() {
        super("User Create Exception");
    }

    public UserCreateException(String message) {
        super(message);
    }

    public UserCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
