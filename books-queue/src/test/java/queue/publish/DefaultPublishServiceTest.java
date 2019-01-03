package queue.publish;

import org.junit.Test;
import queue.dto.AddBooksDto;
import queue.dto.UpdateSaleBooksDto;
import queue.network.RabbitChannelFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yjlee on 2018-12-03.
 */
public class DefaultPublishServiceTest {


    @Test
    public void given_whenbasicPublish_then() throws IOException, TimeoutException {

        RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
        DefaultPublishService defaultPublishService = new DefaultPublishService(rabbitChannelFactory,"books-queue","book");
        AddBooksDto addBooksDto = new AddBooksDto("1234","hello-world" + System.currentTimeMillis(),"yjlee",1000,"20181114",1);
        defaultPublishService.basicPublish(addBooksDto);

//        AddBooksDto addBooksDto = new AddBooksDto("1234","hello-world","yjlee",1000,"20181114",1);
        UpdateSaleBooksDto updateSaleBooksDto = new UpdateSaleBooksDto("1234",1L, 10);
        defaultPublishService.basicPublish(updateSaleBooksDto);

    }


}