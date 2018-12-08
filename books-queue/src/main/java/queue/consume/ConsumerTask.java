package queue.consume;

import lombok.extern.slf4j.Slf4j;
import queue.dto.AddBooksDto;
import queue.dto.QueueDto;
import queue.dto.UpdateSaleBooksDto;

import java.util.function.Consumer;

/**
 * Created by yjlee on 2018-12-03.
 */
@Slf4j
public class ConsumerTask {

    public static Consumer<QueueDto> getConsumer(){
        Consumer<QueueDto> queueDtoConsumer = (queueDto -> {

            switch (queueDto.getChainFunction()){
                case ADD_BOOK:
                    AddBooksDto addBooksDto = (AddBooksDto) queueDto;
                    log.info("ADD BOOK isbn {} , name {} " , addBooksDto.getIsbn() , addBooksDto.getName());
                    break;
                case UPDATE_BOOK:
                    UpdateSaleBooksDto updateSaleBooksDto = (UpdateSaleBooksDto) queueDto;
                    log.info("UPDATE BOOK isbn {} , saleCount {}  " , updateSaleBooksDto.getIsbn() , updateSaleBooksDto.getSalesCount());
                    break;
            }

        });
        return queueDtoConsumer;
    }



}
