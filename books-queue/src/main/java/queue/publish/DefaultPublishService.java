package queue.publish;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import queue.dto.AddBooksDto;
import queue.network.RabbitChannelFactory;
import queue.util.JsonConverter;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class DefaultPublishService implements PublishService{

    Connection connection;
    Channel channel;
    RabbitChannelFactory rabbitChannelFactory;
    String exchangeName;
    String queueName;

    public DefaultPublishService(RabbitChannelFactory connectionFactory, String exchangeName, String queueName) throws IOException, TimeoutException {
        rabbitChannelFactory = connectionFactory;
        this.exchangeName = exchangeName;
        this.queueName = queueName;
    }

    @Override
    public <T> void basicPublish(T t) throws IOException, TimeoutException {
        try {
            this.connection = rabbitChannelFactory.getConnectionFactory().newConnection();
            this.channel = this.connection.createChannel();
            this.channel.exchangeDeclare(exchangeName,"direct",true);
            this.channel.queueDeclare(queueName,true,false,false,null);
            String jsonValue = JsonConverter.toJson(t);
            channel.basicPublish(exchangeName, "", null, jsonValue.getBytes());
        }finally {
            connection.close();
            channel.close();
        }
    }
}
