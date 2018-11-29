package queue.consume;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

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
    public void consume() throws IOException {

        channel.basicConsume("",false,"myConsumerTag", new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);

                String routingKey = envelope.getRoutingKey();
                String contentType = properties.getContentType();
                long deliveryTag = envelope.getDeliveryTag();
                // (process the message components here ...)
                channel.basicAck(deliveryTag, false);


            }
        } );

    }
}
