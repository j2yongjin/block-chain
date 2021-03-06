package com.daou.books.core.service;

import com.daou.books.common.util.JsonConverter;
import com.daou.books.queue.RabbitChannelFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class MqPublish {

    private Connection connection;
    private Channel channel;
    private RabbitChannelFactory rabbitChannelFactory;
    private  String exchangeName;
    private String queueName;

    public MqPublish(RabbitChannelFactory connectionFactory, String exchangeName, String queueName) throws IOException, TimeoutException {
        this.rabbitChannelFactory = connectionFactory;
        this.exchangeName = exchangeName;
        this.queueName = queueName;
    }

    public <T> void basicPublish(T t) throws IOException, TimeoutException {
        try {
            this.connection = rabbitChannelFactory.getConnectionFactory().newConnection();
            this.channel = this.connection.createChannel();
            this.channel.exchangeDeclare(exchangeName,"direct",true);
            this.channel.queueDeclare(queueName,true,false,false,null);
            String jsonValue = JsonConverter.toJson(t);
            channel.basicPublish(exchangeName, "", null, jsonValue.getBytes());
//            channel.waitForConfirms();
            GetResponse getResponse = channel.basicGet(queueName,false);
            log.info(" response : " + getResponse);

        } finally {
            channel.close();
            connection.close();
        }
    }
}
