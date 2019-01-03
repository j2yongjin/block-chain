package consumer.service;

import com.daou.supplier.model.Books;
import com.daou.supplier.service.DefaultBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import queue.dto.AddBooksDto;
import queue.dto.QueueDto;
import queue.dto.UpdateSaleBooksDto;

import java.util.Collections;
import java.util.function.Consumer;

/**
 * Created by yjlee on 2018-12-26.
 */
@Slf4j
public class ChainCodeConsumer {

    static final String COMMIT_COMPLETED_URL = "http://localhost/api/event/book/add";

    public static Consumer<QueueDto> getConsumer(){
        Consumer<QueueDto> queueDtoConsumer = (queueDto -> {

            switch (queueDto.getChainFunction()){
                case ADD_BOOK:
                    AddBooksDto addBooksDto = (AddBooksDto) queueDto;

                    try {
                        DefaultBookService defaultBookService = new DefaultBookService();
                        Books books = new Books(addBooksDto.getIsbn(),addBooksDto.getName(),addBooksDto.getWriter()
                        ,addBooksDto.getAmount(),addBooksDto.getIssueDate(),addBooksDto.getSalesCount());
                        defaultBookService.addBooks(books);

                        // api call chainbook update complete

                    }catch (Exception e){
                        log.error("chaincode invoke addbooks error" ,e);
                        throw new RuntimeException("invoke addbooks error books : " + addBooksDto);
                    }
                    log.info("ADD BOOK isbn {} , name {} " , addBooksDto.getIsbn() , addBooksDto.getName());
                    break;
                case UPDATE_BOOK:
                    UpdateSaleBooksDto updateSaleBooksDto = (UpdateSaleBooksDto) queueDto;

                    try{
                        DefaultBookService defaultBookService = new DefaultBookService();
                        defaultBookService.incrementSalesCount(updateSaleBooksDto.getIsbn(),updateSaleBooksDto.getSalesCount());

                        RestTemplate restTemplate = new RestTemplate();
                        String url = COMMIT_COMPLETED_URL +"/"+updateSaleBooksDto.getIsbn();
                        restTemplate.put(url,null);
//                        restTemplate.put(COMMIT_COMPLETED_URL,updateSaleBooksDto.getIsbn());
                    }catch (Exception e){
                        log.error("chaincode invoke updatebooks error books :" + updateSaleBooksDto);
                    }

                    log.info("UPDATE BOOK isbn {} , saleCount {}  " , updateSaleBooksDto.getIsbn() , updateSaleBooksDto.getSalesCount());
                    break;
            }

        });
        return queueDtoConsumer;
    }

}
