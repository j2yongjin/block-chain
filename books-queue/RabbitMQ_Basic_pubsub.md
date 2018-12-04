### Publish / Subscribe

    이전 튜토리얼에서는 worker queue을 만들었습니다. worker queue 뒤에있는 가정은 각 작업이 정확히 한 명의 worker에게 
    전달된다는 것입니다
    
    이 부분에서는 완전히 다른 것을 할 것입니다. 우리는 여러 소비자에게 메시지를 전달할 것입니다.
    
    이 패턴을 "게시 / 구독"이라고합니다.
    
    패턴을 설명하기 위해 간단한 로깅 시스템을 구축 할 것입니다. 두 가지 프로그램으로 구성됩니다. 
    첫 번째 프로그램은 로그 메시지를 전송하고 두 번째 프로그램은 로그 메시지를 수신하고 출력합니다
    
    로깅 시스템에서는 실행중인 모든 수신 프로그램 사본에 메시지가 표시됩니다. 
    그러면 우리는 하나의 receiver를 실행하고 로그를 디스크로 보낼 수 있습니다. 동시에 
    다른 receiver를 실행하고 화면의 로그를 볼 수 있습니다.
    
    기본적으로 게시 된 로그 메시지는 모든 수신자에게 브로드 캐스팅됩니다.
    
#### Exchanges

    튜토리얼의 이전 부분에서는 queue 주고받은 메시지를 수신했습니다. 이제 Rabbit에서 전체 메시징 모델을 소개 할 차례입니다.
    
    이전 자습서에서 다룬 내용을 빠르게 살펴 보겠습니다
    
    -생성자는 메시지를 보내는 사용자 응용 프로그램입니다.
    -대기열은 메시지를 저장하는 버퍼입니다.
    -소비자는 메시지를받는 사용자 응용 프로그램입니다.
    
    RabbitMQ의 메시징 모델에서 핵심 아이디어는 producer 가 어떤 메시지도 queue로 직접 보내지 않는다는 것입니다. 
    사실, producer는 메시지가 어떤 대기열에 전달되는지 전혀 모릅니다.
    
    대신 producer 는 메시지를 exchange에만 보낼 수 있습니다. 
    exchange 은 아주 간단한 일입니다. 
    한 쪽에서는 생산자로부터 메시지를 받고 다른 쪽에서는 메시지를 queue로 보냅니다. 
    exchange는받은 메시지를 어떻게 처리해야하는지 정확히 알아야합니다. 
    특정 큐에 추가해야합니까? 
    여러 queue에 추가해야합니까? 
    또는 폐기해야합니다. 그 규칙은 교환 유형에 의해 정의됩니다.
    
    사용할 수있는 교환 유형은 direct, topic, header 및 fanout 입니다. 
    마지막 부분 인 fancout 에 집중하겠습니다. 이 유형의 교환을 만들어 로그라고 부르 자.
    
    channel.exchangeDeclare("logs", "fanout");
    
    fanout exchange은 매우 간단합니다. 이름에서 추측 할 수 있듯이, 수신 한 모든 메시지를 알고있는 모든 대기열에 브로드 캐스트합니다. 
    그리고 그것은 우리가 로거에 필요한 것입니다.
    
    
### 샘플 
    
    import java.io.IOException;
    import com.rabbitmq.client.ConnectionFactory;
    import com.rabbitmq.client.Connection;
    import com.rabbitmq.client.Channel;
    
    public class EmitLog {
    
        private static final String EXCHANGE_NAME = "logs";
    
        public static void main(String[] argv)
                      throws java.io.IOException {
    
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
    
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    
            String message = getMessage(argv);
    
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
    
            channel.close();
            connection.close();
        }
        //...
    }


    import com.rabbitmq.client.*;   
    import java.io.IOException;
    
    public class ReceiveLogs {
      private static final String EXCHANGE_NAME = "logs";
    
      public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
    
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
    
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
        Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope,
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
            String message = new String(body, "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
          }
        };
        channel.basicConsume(queueName, true, consumer);
      }
    }
