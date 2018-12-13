package com.daou.fabric.chaincode;

import java.util.Arrays;

/**
 * Created by yjlee on 2018-11-20.
 */
public enum ChainCodeFunction {

    ADD_BOOK("add_book"),INCREMENT_SALES_BOOK("increment_sales_book"),
    FIND_BOOK("getBooksByKey"),FIND_ALL("getAllBooks"),DELETE_BOOK("delete_book");

    String name;
    ChainCodeFunction(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ChainCodeFunction getChainCodeFunction(String name){
        return Arrays.stream(values()).filter( chainCodeFunction -> chainCodeFunction.getName().equals(name)).findAny().orElse(null);
    }
}
