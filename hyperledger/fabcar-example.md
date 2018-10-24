## fabcar example
    https://hyperledger-fabric.readthedocs.io/en/latest/write_first_app.html

## 순서
   1) 개발 환경 구성 
   2) 앱에서 사용할 샘플 스마트 계약의 매개 변수를 학습합니다
   3) 원장에서 자산을 쿼리하고 업데이트 할 수있는 응용 프로그램 개발
   
   
### Install the clients & launch the network

   ./startFabric.sh
   
### docker log 출력

    docker logs -f ca.example.com
   
### 어드민 추가

    npm cache clean --force
    
    npm insall
 
    새로 생성 된 admin eCert를 사용하여 이제 CA 서버와 통신하여 새 사용자를 등록하고 등록합니다
    node enrollAdmin.js
    
    성공 로그 
    2018/10/22 13:36:05 [INFO] signed certificate with serial number 340191001829120559181204968664371397362060633466
    2018/10/22 13:36:05 [INFO] 172.19.0.1:51074 POST /api/v1/enroll 201 0 "OK"

    이 프로그램은 인증서 서명 요청 (CSR)을 호출하고 궁극적으로이 프로젝트의 
    루트에 새로 생성 된 폴더 (hfc-key-store)에 eCert 및 주요 자료를 출력합니다

    drwxrwxr-x 4 yjlee yjlee 4096 10월 24 21:59 ../
    -rw-rw-r-- 1 yjlee yjlee  246 10월 24 21:58 5ec6d26ccaa56ece6c1af94c3c2bb7ce6aa3470710efe0911ee2cfc1c459a817-priv
    -rw-rw-r-- 1 yjlee yjlee  182 10월 24 21:58 5ec6d26ccaa56ece6c1af94c3c2bb7ce6aa3470710efe0911ee2cfc1c459a817-pub
    -rw-rw-r-- 1 yjlee yjlee  986 10월 24 21:58 admin

    
### Register and Enroll user1
    새로 생성 된 admin eCert를 사용하여 이제 CA 서버와 통신하여 새 사용자를 등록하고 등록합니다
    
    node registerUser.js
    
    서버 성공 로그
    2018/10/22 13:39:25 [INFO] 172.19.0.1:51080 POST /api/v1/register 201 0 "OK"
    2018/10/22 13:39:25 [INFO] signed certificate with serial number 357433460539293339108612625914030457375640499572
    2018/10/22 13:39:25 [INFO] 172.19.0.1:51084 POST /api/v1/enroll 201 0 "OK"
    
    클라이언트 성공 로그
    Successfully loaded admin from persistence
    Successfully registered user1 - secret:NlVLUIjZwPPX
    Successfully enrolled member user "user1"
    User1 was successfully registered and enrolled and is ready to interact with the fabric network
    
    인증키 생성 
    -rw-rw-r-- 1 yjlee yjlee  246 10월 24 22:18 3bcfe0a9333edff38e6dd9415133d11791f6918d5894ab53a14c09f37266f8e1-priv
    -rw-rw-r-- 1 yjlee yjlee  182 10월 24 22:18 3bcfe0a9333edff38e6dd9415133d11791f6918d5894ab53a14c09f37266f8e1-pub
    -rw-rw-r-- 1 yjlee yjlee  246 10월 24 21:58 5ec6d26ccaa56ece6c1af94c3c2bb7ce6aa3470710efe0911ee2cfc1c459a817-priv
    -rw-rw-r-- 1 yjlee yjlee  182 10월 24 21:58 5ec6d26ccaa56ece6c1af94c3c2bb7ce6aa3470710efe0911ee2cfc1c459a817-pub
    -rw-rw-r-- 1 yjlee yjlee  986 10월 24 21:58 admin
    -rw-rw-r-- 1 yjlee yjlee 1180 10월 24 22:18 user1



### 조회

    node query.js
    
    내부 소스 
    
    var channel = fabric_client.newChannel('mychannel');
    var peer = fabric_client.newPeer('grpc://localhost:7051');
    channel.addPeer(peer);

    ...
    
    const request = {
            //targets : --- letting this default to the peers assigned to the channel
            chaincodeId: 'fabcar',
            fcn: 'queryAllCars',
            args: ['']
    };

    // send the query proposal to the peer
    return channel.queryByChaincode(request);

    ...

    peer 로딩 로그
    
    2018-10-24 12:58:24.916 UTC [endorser] callChaincode -> INFO 03b [mychannel][3bb11458] Entry chaincode: name:"lscc"
    2018-10-24 12:58:25.532 UTC [endorser] callChaincode -> INFO 03c [mychannel][3bb11458] Exit chaincode: name:"lscc"  (616ms)
    2018-10-24 12:58:28.566 UTC [gossip/election] beLeader -> INFO 03d [61 97 241 125 96 173 40 203 239 81 147 191 102 161 39 23 0 117 69 217 56 120 139 152 120 223 29 9 23 115 238 6] : Becoming a leader
    2018-10-24 12:58:28.566 UTC [gossip/service] func1 -> INFO 03e Elected as a leader, starting delivery service for channel mychannel
    2018-10-24 12:58:28.575 UTC [gossip/privdata] StoreBlock -> INFO 03f [mychannel] Received block [1] from buffer
    2018-10-24 12:58:28.579 UTC [committer/txvalidator] Validate -> INFO 040 [mychannel] Validated block [1] in 3ms
    2018-10-24 12:58:28.581 UTC [cceventmgmt] HandleStateUpdates -> INFO 041 Channel [mychannel]: Handling LSCC state update for chaincode [fabcar]
    2018-10-24 12:58:28.599 UTC [kvledger] CommitWithPvtData -> INFO 042 [mychannel] Committed block [1] with 1 transaction(s) in 20ms (state_validation=3ms block_commit=3ms state_commit=11ms)
    2018-10-24 12:58:35.786 UTC [endorser] callChaincode -> INFO 043 [mychannel][1ebca540] Entry chaincode: name:"fabcar"
    2018-10-24 12:58:35.789 UTC [endorser] callChaincode -> INFO 044 [mychannel][1ebca540] Exit chaincode: name:"fabcar"  (3ms)
    2018-10-24 12:58:37.793 UTC [gossip/privdata] StoreBlock -> INFO 045 [mychannel] Received block [2] from buffer
    2018-10-24 12:58:37.796 UTC [committer/txvalidator] Validate -> INFO 046 [mychannel] Validated block [2] in 2ms
    2018-10-24 12:58:37.837 UTC [couchdb] CreateDatabaseIfNotExist -> INFO 047 Created state database mychannel_fabcar
    2018-10-24 12:58:37.858 UTC [kvledger] CommitWithPvtData -> INFO 048 [mychannel] Committed block [2] with 1 transaction(s) in 61ms (state_validation=2ms block_commit=11ms state_commit=46ms)

    서버 로그 
    2018-10-24 13:27:03.246 UTC [endorser] callChaincode -> INFO 049 [mychannel][60b9f069] Entry chaincode: name:"fabcar"
    2018-10-24 13:27:03.255 UTC [endorser] callChaincode -> INFO 04a [mychannel][60b9f069] Exit chaincode: name:"fabcar"  (8ms)
    
    클라이언트 로그
    Store path:/home/yjlee/fabric-samples/fabcar/hfc-key-store
    (node:4619) DeprecationWarning: grpc.load: Use the @grpc/proto-loader module with grpc.loadPackageDefinition instead
    Successfully loaded user1 from persistence
    Query has completed, checking results
    Response is  [{"Key":"CAR0", "Record":{"colour":"blue","make":"Toyota","model":"Prius","owner":"Tomoko"}},{"Key":"CAR1", "Record":{"colour":"red","make":"Ford","model":"Mustang","owner":"Brad"}},{"Key":"CAR2", "Record":{"colour":"green","make":"Hyundai","model":"Tucson","owner":"Jin Soo"}},{"Key":"CAR3", "Record":{"colour":"yellow","make":"Volkswagen","model":"Passat","owner":"Max"}},{"Key":"CAR4", "Record":{"colour":"black","make":"Tesla","model":"S","owner":"Adriana"}},{"Key":"CAR5", "Record":{"colour":"purple","make":"Peugeot","model":"205","owner":"Michel"}},{"Key":"CAR6", "Record":{"colour":"white","make":"Chery","model":"S22L","owner":"Aarav"}},{"Key":"CAR7", "Record":{"colour":"violet","make":"Fiat","model":"Punto","owner":"Pari"}},{"Key":"CAR8", "Record":{"colour":"indigo","make":"Tata","model":"Nano","owner":"Valeria"}},{"Key":"CAR9", "Record":{"colour":"brown","make":"Holden","model":"Barina","owner":"Shotaro"}}]
    yjlee@yjlee-VirtualBox:~/fabric-samples/fabcar$


    couchdb  로그
    [notice] 2018-10-24T13:28:55.955659Z nonode@nohost <0.29902.0> 94876243eb couchdb:5984 172.18.0.5 undefined GET /mychannel_lscc/fabcar?attachments=true 200 ok 1
    [notice] 2018-10-24T13:28:55.971289Z nonode@nohost <0.29902.0> 56eb53bd5a couchdb:5984 172.18.0.5 undefined GET /mychannel_fabcar/_all_docs?endkey=%22CAR999%22&include_docs=true&inclusive_end=false&limit=1001&startkey=%22CAR0%22 200 ok 11

    
### 추가
    
    node invoke.js

    내부 소스 
    var channel = fabric_client.newChannel('mychannel');
    var peer = fabric_client.newPeer('grpc://localhost:7051');
    channel.addPeer(peer);
    var order = fabric_client.newOrderer('grpc://localhost:7050')
    channel.addOrderer(order);
    
    Orderer 내부 로그
    2018-10-24 13:41:27.285 UTC [orderer/commmon/multichannel] NewRegistrar -> INFO 004 Starting system channel 'testchainid' with genesis block hash bec6cc0cd2f12e6a00c0973252dc28f9eb39eca089d970f5fdbd21238c89b316 and orderer type solo
    2018-10-24 13:41:27.285 UTC [orderer/common/server] Start -> INFO 005 Starting orderer:
     Version: 1.3.0
     Commit SHA: ab0a67a
     Go version: go1.10.4
     OS/Arch: linux/amd64
     Experimental features: false
    2018-10-24 13:41:27.285 UTC [orderer/common/server] Start -> INFO 006 Beginning to serve requests
    2018-10-24 13:41:38.055 UTC [fsblkstorage] newBlockfileMgr -> INFO 007 Getting block information from block storage
    2018-10-24 13:41:38.061 UTC [orderer/commmon/multichannel] newChain -> INFO 008 Created and starting new chain mychannel

    
    