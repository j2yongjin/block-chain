package queue.consume;

import queue.network.RabbitChannelFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * Created by yjlee on 2018-12-03.
 */
public class DefaultConsumerServiceTest {

    public static void main(String[] args) throws IOException, TimeoutException {

        RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
        DefaultConsumerService defaultConsumerService = new DefaultConsumerService(rabbitChannelFactory,"books-queue","book");
        defaultConsumerService.consume((a) -> System.out.printf("hello world"));

    }

}