package com.daou.supplier.service;

/**
 * Created by yjlee on 2018-11-20.
 */
public enum ChainCodeFunction {

    ADD_BOOK("add_book"),INCREMENT_SALES_BOOK("increment_sales_book"),FIND_BOOK("getBooksByKey"),FIND_ALL("getAllBooks");

    String name;
    ChainCodeFunction(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
