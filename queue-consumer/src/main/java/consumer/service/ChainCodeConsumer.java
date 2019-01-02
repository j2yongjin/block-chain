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

    static final String COMMIT_COMPLETED_URL = "/api/event/book/add";

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


                        HttpHeaders headers = new HttpHeaders();
                        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//                        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                        MultiValueMap<String,String> form = new LinkedMultiValueMap<>();
                        form.add("isbn", books.getIsbn());
                        form.add("status", "3");

                        RestTemplate restTemplate = new RestTemplate();
                        ResponseEntity<String> responseEntity =
                                restTemplate.exchange(COMMIT_COMPLETED_URL, HttpMethod.PUT
                                        ,new HttpEntity<>(headers),String.class,form);

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
