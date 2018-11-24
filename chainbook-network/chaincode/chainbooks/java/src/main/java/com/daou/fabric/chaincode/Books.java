package com.daou.fabric.chaincode;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjlee on 2018-10-28.
 */


public class Books implements MakeableArguments{


    @JsonProperty
    String isbn;
    @JsonProperty
    String name;
    @JsonProperty
    String writer;
    @JsonProperty
    Integer amount;
    @JsonProperty
    String issueDate;
    @JsonProperty
    Integer salesCount;

    @JsonProperty
    String doctype = "books";

    public Books(){};

    public Books(String isbn, String name, String writer, Integer amount, String issueDate, Integer salesCount) {
        this.isbn = isbn;
        this.name = name;
        this.writer = writer;
        this.amount = amount;
        this.issueDate = issueDate;
        this.salesCount = salesCount;
    }

    public Books(String isbn, String name, Integer salesCount) {
        this.isbn = isbn;
        this.name = name;
        this.salesCount = salesCount;
    }

    @JsonIgnore
    @Override
    public ArrayList<String> getArgs() {
        ArrayList<String> args = new ArrayList<>();
        if(isNotEmpty(isbn)) args.add(isbn); else throw new RuntimeException("isbn should be value");
        if(isNotEmpty(name)) args.add(name);
        if(isNotEmpty(writer)) args.add(writer);
        if(isValidNumber(amount)) args.add(String.valueOf(amount));
        if(isNotEmpty(issueDate)) args.add(issueDate);
        if(isValidNumber(salesCount)) args.add(String.valueOf(salesCount));
        return args;
    }

//    public String getAsJsonString() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(this);
//    }

    public static Books getNewInstance(List<String> args){
        return new Books(args.get(0),args.get(1),args.get(2),Integer.valueOf(args.get(3))
                ,args.get(4),Integer.valueOf(args.get(5)) );
    }

    public static Books getNewInstanceWithJsonString(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString,Books.class);
    }

    private boolean isNotEmpty(String value) {
        return value!=null && !value.trim().equals("");
    }
    private boolean isValidNumber(Integer amount){
        return amount >0;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public String getWriter() {
        return writer;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public Integer getSalesCount() {
        return salesCount;
    }
}
