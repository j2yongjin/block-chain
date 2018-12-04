package queue.consume;

import lombok.extern.slf4j.Slf4j;
import queue.dto.AddBooksDto;
import queue.dto.QueueDto;

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
                    log.info("ADD BOOK {} " , (AddBooksDto)queueDto);
                    break;
                case UPDATE_BOOK:
                    log.info("UPDATE BOOK {} " , (AddBooksDto)queueDto);
                    break;
            }
        });
        return queueDtoConsumer;
    }



}
