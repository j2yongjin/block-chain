package queue.consume;

import queue.config.QueueConfig;
import queue.dto.QueueDto;
import queue.network.RabbitChannelFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Created by yjlee on 2018-12-03.
 */
public class DefaultConsumerServiceTest {

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
        DefaultConsumerService defaultConsumerService = new DefaultConsumerService(rabbitChannelFactory, QueueConfig.EXCHANGE_NAME,QueueConfig.QUEUE);
//        Consumer<QueueDto> consumer = ConsumerTask.getConsumer();
        defaultConsumerService.consume((a) -> {
            System.out.printf("hello world");
        });

    }

}