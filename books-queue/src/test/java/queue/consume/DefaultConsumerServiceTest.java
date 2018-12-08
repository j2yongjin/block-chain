package queue.consume;

import lombok.extern.slf4j.Slf4j;
import queue.config.QueueConfig;
import queue.dto.QueueDto;
import queue.network.RabbitChannelFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Created by yjlee on 2018-12-03.
 */
public class DefaultConsumerServiceTest {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        RabbitChannelFactory rabbitChannelFactory = new RabbitChannelFactory("localhost");


        ExecutorService executorService = Executors.newFixedThreadPool(20);
        executorService.execute(new TaskTest(rabbitChannelFactory));
        executorService.execute(new TaskTest(rabbitChannelFactory));
        executorService.execute(new TaskTest(rabbitChannelFactory));
        executorService.execute(new TaskTest(rabbitChannelFactory));
        executorService.execute(new TaskTest(rabbitChannelFactory));
        executorService.execute(new TaskTest(rabbitChannelFactory));

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MICROSECONDS);

//        DefaultConsumerService defaultConsumerService = new DefaultConsumerService(rabbitChannelFactory, QueueConfig.EXCHANGE_NAME,QueueConfig.QUEUE);
////        Consumer<QueueDto> consumer = ConsumerTask.getConsumer();
//        defaultConsumerService.consume((a) -> {
//            System.out.printf("hello world");
//        });



    }



}

@Slf4j
class TaskTest implements Runnable{

    RabbitChannelFactory rabbitChannelFactory;

    public TaskTest(RabbitChannelFactory rabbitChannelFactory) {
        this.rabbitChannelFactory = rabbitChannelFactory;
    }

    @Override
    public void run() {
        DefaultConsumerService defaultConsumerService = null;
        try {
            defaultConsumerService = new DefaultConsumerService(rabbitChannelFactory, QueueConfig.EXCHANGE_NAME,QueueConfig.QUEUE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
//        Consumer<QueueDto> consumer = ConsumerTask.getConsumer();
        try {
            defaultConsumerService.consume((a) -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("hello world");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}