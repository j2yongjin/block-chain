package consumer.application;

import lombok.extern.slf4j.Slf4j;
import queue.config.QueueConfig;
import queue.consume.ConsumerTask;
import queue.consume.DefaultConsumerService;
import queue.dto.QueueDto;
import queue.network.RabbitChannelFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * Created by yjlee on 2018-12-05.
 */
@Slf4j
public class MainApplication {

    static final int THREAD_POOL_COUNT = 5;

    public static void main(String[] args) throws IOException, TimeoutException {
        try {
            RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_COUNT);
            for(int i=0;i<THREAD_POOL_COUNT;i++){
                executorService.execute(new consumer.task.ConsumerTask(rabbitChannelFactory));
            }
        }catch (Exception e){
            log.error("Consumer Thread Exception " , e);
        }

    }
}
