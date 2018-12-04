package queue.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import queue.dto.AddBooksDto;
import queue.dto.QueueDto;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by yjlee on 2018-12-03.
 */
public class JsonConverterTest {

    @Test
    public void given_whenToJson_thenSuccess() throws JsonProcessingException {
        AddBooksDto addBooksDto = new AddBooksDto("1234","hello-world","yjlee",1000,"20181114",1);
        String value = JsonConverter.toJson(addBooksDto);
        System.out.printf("value : " + value);
    }

    @Test
    public void given_whenToObject_thenSuccess() throws IOException {
        AddBooksDto addBooksDto = new AddBooksDto("1234","hello-world","yjlee",1000,"20181114",1);
        String value = JsonConverter.toJson(addBooksDto);
        QueueDto queueDto = JsonConverter.toObject(value.getBytes());
        System.out.printf("addbook1 : " +  queueDto.getChainFunction());
    }



}