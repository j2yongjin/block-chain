
## Private Block Chain

### Hyperledger fabric tuturials
   https://hyperledger-fabric.readthedocs.io/en/latest/tutorials.html  
 
### ChainBooks 아키텍쳐

     web1   --> queue   ---> block-chain-consumer(daemon)                     --->   hyperledger network
                           ---> chain-code-supplier    ---> books-chaincode
                                                       <--- Response
     
     web2   -->
                               
### webservice
   
### queue
  #### Queue 등록 항목
     상품 등록
     상품 판매 카운트 업데이트
  #### Queue
     - erlang 설치
       다운로드 : http://www.erlang.org/downloads
       
     - rabbit MQ server 설치
       1)다운로드 : http://www.rabbitmq.com/install-windows.html
       2)설치
         c:/Program Files/RabbitMQ Server/rabbitmq_server-x.x.x/sbin 으로 이동후
         >rabbitmq-plugins enable rabbitmq_management
       3)실행 
         - 어드민 : http://localhost:15672 , guest / guest 
       
     - rabbitMQ Client 설치
       
### block-chain-consumer (daemon)
  #### Consumer
  상품 등록
  상품 판매 카운트 업데이트
  
### chain-code-supplier
  chaincode 호출 클라이언트
  
  주요 method
  1) 어드민 등록 호출
  2) 사용자 등록 호출
  3) 판매 책 등록 호출
  4) 판매 책 조회 ( findByKey , findAll) 호출
  5) 판매 카운트 업데이트 호츨
  6) 판매 책 삭제 호츨
  
### books-chaincode

  chaincode   
  1) 판매 책 등록
  2) 판매 책 조회 ( findByKey , findAll)
  3) 판매 카운트 업데이트
  4) 판매 책 삭제
  
  
### hyperledger network
   chainbook-network 참조
     

   
 