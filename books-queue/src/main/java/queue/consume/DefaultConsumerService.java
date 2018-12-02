package queue.consume;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import queue.dto.AddBooksDto;
import queue.util.JsonConverter;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class DefaultConsumerService implements ConsumerService {


    Channel channel;

    public DefaultConsumerService(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void consume(Consumer consumer) throws IOException {

        channel.basicConsume("",false,"myConsumerTag", new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                String routingKey = envelope.getRoutingKey();
                String contentType = properties.getContentType();
                long deliveryTag = envelope.getDeliveryTag();

//                String recvData = new String(body);
                AddBooksDto addBooksDto = JsonConverter.toObject(body);

                // (process the message components here ...)
                channel.basicAck(deliveryTag, false);
            }
        } );

    }
}
