### Tutorials 
    https://www.rabbitmq.com/getstarted.html
    
### Hello world java
    https://www.rabbitmq.com/tutorials/tutorial-one-java.html
   
   
#### Introduction
    RabbitMQ는 메시지 브로커입니다. 메시지를 수락하고 전달합니다. 우체국이라고 생각할 수 있습니다. 게시하려는 메일을 게시 상자에 넣으면 
    Mail person이 최종적으로 메일을 받는 사람에게 배달되는지 확인할 수 있습니다. 이 유추에서 RabbitMQ는 우편함, 우체국 및 우편배달부 입니다.
    
    RabbitMQ와 우체국의 가장 큰 차이점은 종이를 다루지 않는다는 것입니다. 대신에 이진  BLOB 을 받아서 저장하고 전달합니다.
     
    RabbitMQ와 일반적으로 메시징은 전문 용어를 사용합니다.
    
    생산은 보내는 것 이상을 의미하지 않습니다. 메시지를 보내는 프로그램은 제작자입니다.
    
    Queue 는 RabbitMQ 내부에있는 post box의 이름입니다. 메시지는 RabbitMQ 및 응용 프로그램을 통해 전달되지만 메시지는 queue에만 저장할 수 있습니다. 
    queue은 호스트의 메모리 및 디스크 제한에 의해서만 제한되며, 본질적으로 큰 메시지 버퍼입니다. 많은 producer가 하나의 
    queue로 이동하는 메시지를 보낼 수 있으며 많은 consumer가 하나의 queue에서 데이터를 수신하려고 시도 할 수 있습니다. 
    이것이 우리가 queue을 표현하는 방법입니다.
    
    cusuming 받는 것과 비슷한 의미를 갖는다. consumer 는 주로 메시지를 받기를 기다리는 프로그램입니다.
    
    producer, consumer 및 broker가 동일한 호스트에 상주 할 필요는 없습니다. 
    실제로 대부분의 응용 프로그램에서는 그렇지 않습니다. 응용 프로그램은 producer와 consumer가 될 수 있습니다
    
    튜토리얼의이 부분에서는 Java로 두 개의 프로그램을 작성합니다. 
    단일 메시지를 보내는 producer 및 메시지를 수신하여 출력하는 consumer가 있습니다. 
    우리는 자바 API의 세부 사항에 대해 집중적으로 살펴볼 것입니다. 시작하는 바로 이 간단한 것에 집중하십시오. 메시징의 "hello world"입니다.
    
#### Sending

    메시지 publisher (발신자) 보내기 및 메시지 소비자 (수신자) Recv. publisher RabbitMQ에 연결하고 단일 메시지를 보낸 다음 종료합니다.
    
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    연결은 소켓 연결을 추상화하고 프로토콜 버전 협상 및 인증 등을 처리합니다. 
    여기에서 우리는 로컬 시스템의 브로커에 연결합니다. 따라서 로컬 호스트. 
    다른 머신의 브로커에 연결하고 싶다면 여기에 이름이나 IP 주소를 지정하기 만하면됩니다.
    
    다음으로 우리는 일을 처리하기위한 대부분의 API가 있는 채널을 만듭니다.
    
    보내려면 우리가 보낼 대기열을 선언해야합니다. 큐에 메시지를 게시 할 수 있습니다.
    
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");

    큐를 선언하는 것은 멱등 원입니다. 이미 존재하지 않는 경우에만 생성됩니다. 
    메시지 내용은 바이트 배열이므로 원하는대로 인코딩 할 수 있습니다
    
    마지막으로 채널과 연결을 닫습니다.
    
    import com.rabbitmq.client.Channel;
    import com.rabbitmq.client.Connection;
    import com.rabbitmq.client.ConnectionFactory;
    
    public class Send {
    
      private final static String QUEUE_NAME = "hello";
    
      public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
    
        channel.close();
        connection.close();
      }
    }
    
    Sending doesn't work!
    
    
    RabbitMQ를 처음 사용하는 경우 "보낸"메시지가 표시되지 않으면 머리를 긁적 거리며 무엇이 잘못 될 수 있는지 궁금 할 수 있습니다. 
    브로커가 충분한 여유 디스크 공간없이 시작되었거나 (기본적으로 최소 200MB의 여유 공간이 필요함) 메시지를 수락하지 않을 수 있습니다. 
    필요한 경우 브로커 로그 파일을 확인하여 한계를 확인하고 줄이십시오. 설정 파일 문서는 disk_free_limit를 설정하는 방법을 보여줍니다.
    
#### Receiving

    그것이 우리 publisher 를위한 것입니다. 우리의 consumer는 RabbitMQ에서 푸시 된 메시지이므로 단일 메시지를 게시하는 게시자와 
    달리 메시지를 수신하여 출력합니다
    
    여분의 DefaultConsumer는 서버가 푸시 한 메시지를 버퍼링하는 데 사용할 Consumer 인터페이스를 구현하는 클래스입니다.
    
    설정은 publisher와 동일합니다. 우리는 연결과 채널을 열고, 우리가 소비 할 큐를 선언합니다. 이것은 전송할 큐와 일치합니다.
    
    public class Recv {
      private final static String QUEUE_NAME = "hello";
    
      public static void main(String[] argv)
          throws java.io.IOException,
                 java.lang.InterruptedException {
    
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        ...
        }
    }

    여기서도 queue을 선언합니다. publisher 보다 먼저 consumber를 시작하기 때문에 메시지를 사용하기 전에 대기열이 있는지 확인해야합니다.
    
    우리는 대기열에서 메시지를 우리에게 배달하라고 서버에 알리고 있습니다. 
    메시지를 비동기 적으로 전달하기 때문에 우리는 메시지를 사용할 준비가 될 때까지 메시지를 버퍼링 할 객체의 형태로 콜백을 제공합니다. 이것이 DefaultConsumer 하위 클래스의 기능입니다.
    
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope,
                                 AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'");
      }
    };
    channel.basicConsume(QUEUE_NAME, true, consumer);
    
    
    
    import com.rabbitmq.client.*;
    
    import java.io.IOException;
    
    public class Recv {
    
      private final static String QUEUE_NAME = "hello";
    
      public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
        Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
          }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
      }
    }
    

    
    
    
    
    
   
   
   
   