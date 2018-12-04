### Work Queues


    첫 번째 튜토리얼에서는 명명 된 큐에서 메시지를주고받는 프로그램을 작성했습니다. 
    여기서는 여러 작업자에게 시간이 많이 드는 작업을 배포하는 데 사용할 worker queue을 만듭니다.
    
    worker queue (작업 대기열이라고도 함)의 주된 아이디어는 자원 집약적 인 작업을 즉시 수행하지 않고 완료 될 때까지 기다리지 않아야한다는 것입니다. 
    대신에 나중에 수행 할 작업을 예약합니다. 
    우리는 작업을 메시지로 캡슐화하여 대기열로 보냅니다. 
    백그라운드에서 실행중인 worker process는 작업을 팝업하고 결국 작업을 실행합니다. 많은 작업자를 실행하면 작업이 그들 사이에 공유됩니다.
    
    이 개념은 짧은 HTTP 요청 창에서 복잡한 작업을 처리하는 것이 불가능한 웹 응용 프로그램에서 특히 유용합니다
    
#### Preparation(예비)

    이 튜토리얼의 이전 부분에서는 "Hello World!"라는 메시지를 보냈다. 이제 복잡한 작업을 나타내는 문자열을 보냅니다. 크기를 조정할 이미지 나 렌더링 할 PDF 파일과 같은 
    실제 작업이 없으므로 Thread.sleep () 함수를 사용하여 바쁜 것처럼 가장하여 가짜로 만들어 보겠습니다. 문자열의 점 수를 복잡도로 취합니다. 
    모든 점은 1 초의 "일"을 나타낼 것입니다. 예를 들어, Hello ...로 표시된 가짜 작업은 3 초가 걸립니다.
    
    
    java -cp $CP Worker
    # => [*] Waiting for messages. To exit press CTRL+C
    # => [x] Received 'First message.'
    # => [x] Received 'Third message...'
    # => [x] Received 'Fifth message.....'
    
    java -cp $CP Worker
    # => [*] Waiting for messages. To exit press CTRL+C
    # => [x] Received 'Second message..'
    # => [x] Received 'Fourth message....'
    


    import java.io.IOException;
    import com.rabbitmq.client.ConnectionFactory;
    import com.rabbitmq.client.Connection;
    import com.rabbitmq.client.Channel;
    import com.rabbitmq.client.MessageProperties;
    
    public class NewTask {
    
      private static final String TASK_QUEUE_NAME = "task_queue";
    
      public static void main(String[] argv)
                          throws java.io.IOException {
    
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    
        String message = getMessage(argv);
    
        channel.basicPublish( "", TASK_QUEUE_NAME,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
    
        channel.close();
        connection.close();
      }
      //...
    }


    And our Worker.java:
    import com.rabbitmq.client.*;
    
    import java.io.IOException;
    
    public class Worker {
      private static final String TASK_QUEUE_NAME = "task_queue";
    
      public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
    
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
        channel.basicQos(1);
    
        final Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
            String message = new String(body, "UTF-8");
    
            System.out.println(" [x] Received '" + message + "'");
            try {
              doWork(message);
            } finally {
              System.out.println(" [x] Done");
              channel.basicAck(envelope.getDeliveryTag(), false);
            }
          }
        };
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
      }
    
      private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
          if (ch == '.') {
            try {
              Thread.sleep(1000);
            } catch (InterruptedException _ignored) {
              Thread.currentThread().interrupt();
            }
          }
        }
      }
    }

