package com.daou.supplier.service;

import com.daou.supplier.model.Books;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by yjlee on 2018-11-22.
 */
public class JsonTest {

    @Test
    public void givenJsonString_whenReadValue_then() throws IOException {

//        String jsonString = "{'amount':1000,'doctype':'books','isbn':'1111-222-333','name':'hello blockchain','sales_count':0,'writer':'lee'}";
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        TestBooks books = objectMapper.readValue(jsonString, TestBooks.class);
//        System.out.println("books : " + books);

        Books books = new Books("112-33-22","hello block chain","zone",1000, "20181123",0);

        ObjectMapper objectMapper = new ObjectMapper();
        String asJsonString = objectMapper.writeValueAsString(books);
        System.out.println(asJsonString);


        Books result = Books.getNewInstanceWithJsonString(asJsonString);

        System.out.println(" result : " + result.getName());

//        ObjectMapper objectMapper = new ObjectMapper();
        String writevalue = objectMapper.writeValueAsString(books);
        System.out.println("writeValue : " + writevalue);

        Books books1 = objectMapper.readValue(writevalue,Books.class);

        System.out.println(books1.getAmount());
    }


}


