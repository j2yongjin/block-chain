package consumer.rabbitmq.network;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class RabbitConnection {

    ConnectionFactory connectionFactory;
    Connection connection;
    Channel channel;

    public RabbitConnection(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    private void newConnection() throws IOException, TimeoutException {
        connection = connectionFactory.newConnection();
    }

    private void newChannel() throws IOException {
        channel = connection.createChannel();
    }

    public Connection getConnection() throws IOException, TimeoutException {
        return connection;
    }

    public void closeConnection() throws IOException {
        connection.close();
    }

    public Channel getChannel() throws IOException {
        return channel;
    }

    public void closeChannel() throws IOException, TimeoutException {
        channel.close();
    }



}
