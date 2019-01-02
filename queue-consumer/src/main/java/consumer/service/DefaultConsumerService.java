package consumer.service;


import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import queue.dto.QueueDto;
import queue.exception.ConsumerException;
import queue.network.RabbitChannelFactory;
import queue.util.JsonConverter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
@Slf4j
public class DefaultConsumerService implements ConsumerService {

    Channel channel;
    Connection connection;
    RabbitChannelFactory rabbitChannelFactory;
    String exhangeName;
    String queueName;

    public DefaultConsumerService(RabbitChannelFactory connectionFactory, String exchangeName, String queueName) throws IOException, TimeoutException {
        this.rabbitChannelFactory = connectionFactory;
        this.exhangeName = exchangeName;
        this.queueName = queueName;
        this.channel = channel;

        this.connection = rabbitChannelFactory.getConnectionFactory().newConnection();
        this.channel = this.connection.createChannel();
        this.channel.exchangeDeclare(exchangeName,"direct",true);
        this.channel.queueDeclare(queueName,true,false,false,null);
        this.channel.queueBind(queueName,exchangeName,"");

        log.info(" Consumer Service Start");
    }

    @Override
    public void consume(Consumer consumer) throws IOException {

        channel.basicConsume(queueName,false, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                try {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    long deliveryTag = envelope.getDeliveryTag();

                    QueueDto queueDto = JsonConverter.toObject(body);
                    consumer.accept(queueDto);
                    channel.basicAck(deliveryTag, false);
                }catch (Exception e){
                    throw new ConsumerException("Consumer Exception",e);
                }
            }
        } );

    }
}
