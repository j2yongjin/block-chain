package queue.publish;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import queue.dto.AddBooksDto;
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

    public DefaultPublishService(Connection connection) throws IOException {
        this.connection = connection;
        this.channel = connection.createChannel();
    }

    @Override
    public <T> void basicPublish(T t) throws IOException, TimeoutException {

        if(t instanceof AddBooksDto) {
            AddBooksDto addBooksDto = (AddBooksDto) t;
            String jsonValue = JsonConverter.toJson(addBooksDto);
            channel.basicPublish("", "", null, jsonValue.getBytes());
        }
        channel.close();

    }
}
