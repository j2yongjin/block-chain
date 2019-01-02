package com.daou.books.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    private JsonConverter(){
        throw new RuntimeException("Not Support Construct");
    }

    public static <T> String toJson(T t) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(t);
        return jsonValue;
    }

}
