package com.daou.books.book.controller;

import com.daou.books.book.service.DaouBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DaouBooksController {

    @Autowired
    private DaouBookService daouBookService;

    @RequestMapping(name = "/", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        daouBookService.getBookList();
        return "hello";
    }
}
