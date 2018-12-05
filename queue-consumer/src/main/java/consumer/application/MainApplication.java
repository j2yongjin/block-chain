package consumer.application;

import queue.config.QueueConfig;
import queue.consume.ConsumerTask;
import queue.consume.DefaultConsumerService;
import queue.dto.QueueDto;
import queue.network.RabbitChannelFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Created by yjlee on 2018-12-05.
 */
public class MainApplication {

    public static void main(String[] args) throws IOException, TimeoutException {

        try {
            RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
            DefaultConsumerService defaultConsumerService = new DefaultConsumerService(rabbitChannelFactory, QueueConfig.EXCHANGE_NAME, QueueConfig.QUEUE);
            Consumer<QueueDto> consumer = ConsumerTask.getConsumer();
            defaultConsumerService.consume(consumer);
        }catch (Exception e){

        }

    }
}
