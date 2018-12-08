package consumer.invoke;

import com.daou.supplier.model.Books;
import com.daou.supplier.service.DefaultBookService;
import lombok.extern.slf4j.Slf4j;
import queue.dto.AddBooksDto;
import queue.dto.QueueDto;
import queue.dto.UpdateSaleBooksDto;

import java.util.function.Consumer;

/**
 * Created by yjlee on 2018-12-05.
 */
@Slf4j
public class InvokeChaincode {

    public static Consumer<QueueDto> invoke(){
        Consumer<QueueDto> queueDtoConsumer = (queueDto -> {

            try {
                DefaultBookService defaultBookService = new DefaultBookService();

                switch (queueDto.getChainFunction()){
                    case ADD_BOOK:
                        AddBooksDto addBooksDto = (AddBooksDto) queueDto;
                        log.info("ADD BOOK isbn {} , name {} " , addBooksDto.getIsbn() , addBooksDto.getName());
                        Books books = new Books(addBooksDto.getIsbn(), addBooksDto.getName(), addBooksDto.getWriter()
                                , addBooksDto.getAmount(), addBooksDto.getIssueDate(), addBooksDto.getSalesCount());
                        defaultBookService.addBooks(books);
                        break;
                    case UPDATE_BOOK:
                        UpdateSaleBooksDto updateSaleBooksDto = (UpdateSaleBooksDto) queueDto;
                        log.info("UPDATE BOOK isbn {} , saleCount {}  " , updateSaleBooksDto.getIsbn() , updateSaleBooksDto.getSalesCount());
                        defaultBookService.incrementSalesCount(updateSaleBooksDto.getIsbn(),updateSaleBooksDto.getSalesCount());

                        break;
                }
            } catch (Exception e) {
                throw new RuntimeException("defaultBookService call error " , e);
            }

        });
        return queueDtoConsumer;
    }
}
