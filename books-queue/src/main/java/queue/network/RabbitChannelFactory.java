package queue.network;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * block-chain
 *
 * @auther : yjlee
 * @date : 2018-11-29
 * @desc :
 */
public class RabbitChannelFactory {

    String username;
    String password;
    String virtualHost;
    String hostName;
    Integer portNumber;
    ConnectionFactory connectionFactory;

    public RabbitChannelFactory(String hostName){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostName);
    }

    public RabbitChannelFactory(String username, String password, String virtualHost, String hostName, Integer portNumber) {
        this.username = username;
        this.password = password;
        this.virtualHost = virtualHost;
        this.hostName = hostName;
        this.portNumber = portNumber;

        createConnectionFactory();
    }

    private void createConnectionFactory(){
        connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(hostName);
        connectionFactory.setPort(portNumber);
    }

    public ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }


}
