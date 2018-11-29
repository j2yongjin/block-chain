package consumer.rabbitmq.exchange;

import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class RabbitExchange {


    String exchangeName;
    String queueName;
    Channel channel;

    public RabbitExchange(String exchangeName, String queueName, Channel channel) {
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.channel = channel;
    }

    private void createExchangeAndQueue() throws IOException {
        channel.exchangeDeclare(exchangeName,"direct",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,"1");
    }
}
