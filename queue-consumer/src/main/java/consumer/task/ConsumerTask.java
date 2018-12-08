package consumer.task;

import lombok.extern.slf4j.Slf4j;
import queue.config.QueueConfig;
import queue.consume.DefaultConsumerService;
import queue.dto.QueueDto;
import queue.network.RabbitChannelFactory;

import java.util.function.Consumer;

/**
 * Created by yjlee on 2018-12-05.
 */
@Slf4j
public class ConsumerTask implements Runnable {

    RabbitChannelFactory rabbitChannelFactory;

    public ConsumerTask(RabbitChannelFactory rabbitChannelFactory) {
        this.rabbitChannelFactory = rabbitChannelFactory;
    }

    @Override
    public void run() {
        try {
            DefaultConsumerService defaultConsumerService = new DefaultConsumerService(rabbitChannelFactory, QueueConfig.EXCHANGE_NAME, QueueConfig.QUEUE);
            Consumer<QueueDto> consumer = queue.consume.ConsumerTask.getConsumer();
            defaultConsumerService.consume(consumer);
        }catch (Exception e){
            log.error("Consumer task Error" ,e);
        }
    }
}
