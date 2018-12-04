package queue.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import queue.dto.AddBooksDto;
import queue.dto.UpdateSaleBooksDto;

import java.io.IOException;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class JsonConverter {

    private JsonConverter(){
        throw new RuntimeException("Not Support Construct");
    }

    public static <T> String toJson(T t) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(t);
        return jsonValue;
    }

    public static <T> T toObject(byte[] body) throws IOException {
        String textBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        if(textBody.contains("ADD_BOOK")) {
            return objectMapper.readValue(textBody, (Class<T>) AddBooksDto.class);
        }else{
            return objectMapper.readValue(textBody, (Class<T>) UpdateSaleBooksDto.class);
        }

    }



}
