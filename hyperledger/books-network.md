
## 프로젝트 네트워크 구성

### 샘플 네트워크 분석

   ~/fabric-samples/first-network
   
#### byfn.sh 스크립트 구성
 
./byfn.sh up

    function networkUp() {
      checkPrereqs
      # generate artifacts if they don't exist
      if [ ! -d "crypto-config" ]; then
        generateCerts
        replacePrivateKey
        generateChannelArtifacts
      fi
      if [ "${IF_COUCHDB}" == "couchdb" ]; then
        IMAGE_TAG=$IMAGETAG docker-compose -f $COMPOSE_FILE -f $COMPOSE_FILE_COUCH up -d 2>&1
      else
        IMAGE_TAG=$IMAGETAG docker-compose -f $COMPOSE_FILE up -d 2>&1
      fi
      if [ $? -ne 0 ]; then
        echo "ERROR !!!! Unable to start network"
        exit 1
      fi
      # now run the end to end script
      docker exec cli scripts/script.sh $CHANNEL_NAME $CLI_DELAY $LANGUAGE $CLI_TIMEOUT $VERBOSE
      if [ $? -ne 0 ]; then
        echo "ERROR !!!! Test failed"
        exit 1
      fi
    }
    
    
generateCerts    
   
    cryptogen 도구를 사용하여 암호 자료 (x509 certs)를 생성합니다.
     우리의 다양한 네트워크 엔터티. 인증서는 표준 PKI를 기반으로합니다.
     공통 트러스트 앵커에 도달하여 유효성을 검사하는 구현
     
     Cryptogen은 네트워크를 포함하는 파일``crypto-config.yaml``을 사용합니다.
      토폴로지를 사용하여 두 가지 모두에 대한 인증서 라이브러리를 생성 할 수 있습니다.
     조직 및 해당 조직에 속하는 구성 요소. 마다
     조직은 고유 한 루트 인증서 (``ca-cert``)를 제공합니다.
      특정 구성 요소 (동료 및 주문자)를 해당 Org. 거래 및 통신
      within Fabric은 엔티티의 개인 키 ( "keystore")에 의해 서명 된 후 검증됩니다
      공개 키 (``signcerts``)를 사용하여. "count"변수가 있음을 알 수 있습니다.
      이 파일. 우리는이를 사용하여 조직 당 피어의 수를 지정합니다. 우리의
      Org 당 두 명의 동료가있는 경우입니다.
       
      이 도구를 실행하면 certs가 "crypto-config"라는 폴더에 보관됩니다

    cryptogen 도구를 사용하여 조직 certs 생성
    
    # Generates Org certs using cryptogen tool
    function generateCerts() {
      which cryptogen
      if [ "$?" -ne 0 ]; then
        echo "cryptogen tool not found. exiting"
        exit 1
      fi
      echo
      echo "##########################################################"
      echo "##### Generate certificates using cryptogen tool #########"
      echo "##########################################################"
    
      if [ -d "crypto-config" ]; then
        rm -Rf crypto-config
      fi
      set -x
      cryptogen generate --config=./crypto-config.yaml
      res=$?
      set +x
      if [ $res -ne 0 ]; then
        echo "Failed to generate certificates..."
        exit 1
      fi
      echo
    }
    
replacePrivateKey
    
    function replacePrivateKey() {
      # sed on MacOSX does not support -i flag with a null extension. We will use
      # 't' for our back-up's extension and delete it at the end of the function
      ARCH=$(uname -s | grep Darwin)
      if [ "$ARCH" == "Darwin" ]; then
        OPTS="-it"
      else
        OPTS="-i"
      fi
    
      # Copy the template to the file that will be modified to add the private key
      cp docker-compose-e2e-template.yaml docker-compose-e2e.yaml
    
      # The next steps will replace the template's contents with the
      # actual values of the private key file names for the two CAs.
      CURRENT_DIR=$PWD
      cd crypto-config/peerOrganizations/org1.example.com/ca/
      PRIV_KEY=$(ls *_sk)
      cd "$CURRENT_DIR"
      sed $OPTS "s/CA1_PRIVATE_KEY/${PRIV_KEY}/g" docker-compose-e2e.yaml
      cd crypto-config/peerOrganizations/org2.example.com/ca/
      PRIV_KEY=$(ls *_sk)
      cd "$CURRENT_DIR"
      sed $OPTS "s/CA2_PRIVATE_KEY/${PRIV_KEY}/g" docker-compose-e2e.yaml
      # If MacOSX, remove the temporary backup of the docker-compose file
      if [ "$ARCH" == "Darwin" ]; then
        rm docker-compose-e2e.yamlt
      fi
    }



    /*
    `configtxgen 도구는 네 개의 아티팩트를 생성하는 데 사용됩니다 : 주문자 ** 부트 스트랩
    블록 **, 패브릭 ** 채널 구성 트랜잭션 **, 두 개의 ** 앵커
    피어 트랜잭션 ** - 각 피어 조직마다 하나씩.
    
    주문자 블록은 주문 서비스의 기원 블록이며 채널 트랜잭션 파일은 채널 생성시 주문자에게 방송됩니다.
    시각. 앵커 피어 트랜잭션은 이름에서 알 수 있듯이 각각을 지정합니다 조직의 앵커 피어가이 채널에 있습니다.
    Configtxgen은 정의를 포함하는 파일``configtx.yaml``을 사용합니다.
     샘플 네트워크 용. Orderer Org ( "Orderer Org"), Orderer Org 두 개의 피어 노드 ( "Org1"및 "Org2")는 각각 두 개의 피어 노드를 관리하고 유지 관리합니다.
     이 파일은 또한 컨소시엄을 지정합니다 - "SampleConsortium"- 우리의 두 피어 조직. 맨 위에있는 '프로필'섹션에 특히주의하십시오.
     이 파일. 두 개의 고유 한 헤더가 있음을 알 수 있습니다. Order service  대한 genesis block -``TwoOrgsOrdererGenesis`` - 우리 채널을위한 것 -``TwoOrgsChannel``.
     이 헤더는 중요합니다. 우리가 생성 할 때 인수로 전달합니다.
     우리의 유물. 이 파일에는 두 가지 추가 사양이 포함되어 있습니다.
     주목. 첫째, 각 피어 조직에 대한 앵커 피어를 지정합니다 (``peer0.org1.example.com``과``peer0.org2.example.com``). 
     둘째,각 구성원에 대한 MSP 디렉토리의 위치는 차례로 우리가 발주자 생성 블록의 각 조직에 대한 루트 인증서. 이것은 중요하다.
     개념. 이제 주문 서비스와 통신하는 모든 네트워크 엔티티는 그것의 디지털 서명이 확인되었습니다.
    
     이 함수는 암호 자료와 네 가지 구성 artifacts 을 생성합니다.
     이 파일들을 폴더에``channel-artifacts''에 출력한다.
     
     다음과 같은 경고 메시지가 나타나면 무시해도됩니다.
    
     [bccsp] GetDefault -> WARN 001 BCCSP를 사용하기 전에 InitFactories ()를 호출하십시오. bootBCCSP로 다시 떨어집니다.
    
     중간 인증서에 관한 로그는 무시할 수 있습니다.
     
     orderer genesis block 생성한다.
     */
     
     function generateChannelArtifacts() {
       which configtxgen
       if [ "$?" -ne 0 ]; then
         echo "configtxgen tool not found. exiting"
         exit 1
       fi
     
       echo "##########################################################"
       echo "#########  Generating Orderer Genesis block ##############"
       echo "##########################################################"
       # Note: For some unknown reason (at least for now) the block file can't be
       # named orderer.genesis.block or the orderer will fail to launch!
       set -x
       configtxgen -profile TwoOrgsOrdererGenesis -outputBlock ./channel-artifacts/genesis.block
       res=$?
       set +x
       if [ $res -ne 0 ]; then
         echo "Failed to generate orderer genesis block..."
         exit 1
       fi
       echo
       echo "#################################################################"
       echo "### Generating channel configuration transaction 'channel.tx' ###"
       echo "#################################################################"
       set -x
       configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./channel-artifacts/channel.tx -channelID $CHANNEL_NAME
       res=$?
       set +x
       if [ $res -ne 0 ]; then
         echo "Failed to generate channel configuration transaction..."
         exit 1
       fi
     
       echo
       echo "#################################################################"
       echo "#######    Generating anchor peer update for Org1MSP   ##########"
       echo "#################################################################"
       set -x
       configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/Org1MSPanchors.tx -channelID $CHANNEL_NAME -asOrg Org1MSP
       res=$?
       set +x
       if [ $res -ne 0 ]; then
         echo "Failed to generate anchor peer update for Org1MSP..."
         exit 1
       fi
     
       echo
       echo "#################################################################"
       echo "#######    Generating anchor peer update for Org2MSP   ##########"
       echo "#################################################################"
       set -x
       configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate \
         ./channel-artifacts/Org2MSPanchors.tx -channelID $CHANNEL_NAME -asOrg Org2MSP
       res=$?
       set +x
       if [ $res -ne 0 ]; then
         echo "Failed to generate anchor peer update for Org2MSP..."
         exit 1
       fi
       echo
     }

### docker-compose-cli.yaml 디폴트 compose 파일 

    // 외부 디스크 연동
    volumes:
      orderer.example.com:
      peer0.org1.example.com:
      peer1.org1.example.com:
      peer0.org2.example.com:
      peer1.org2.example.com:

    // orderer 설정
    services:
   
      orderer.example.com:
        extends:
          file:   base/docker-compose-base.yaml    // 기본 docker file
          service: orderer.example.com
        container_name: orderer.example.com
        networks:
          - byfn
          
    services:
    
      peer0.org1.example.com:
        container_name: peer0.org1.example.com     // domain
        extends:
          file:  base/docker-compose-base.yaml    // 기본 docker file
          service: peer0.org1.example.com
        networks:
          - byfn
    
      peer1.org1.example.com:
        container_name: peer1.org1.example.com
        extends:
          file:  base/docker-compose-base.yaml
          service: peer1.org1.example.com
        networks:
          - byfn
          
          
       // org2 생략
       ...
       
       // cli docker 사용 이용
       cli:
           container_name: cli
           image: hyperledger/fabric-tools:$IMAGE_TAG
           tty: true
           stdin_open: true
           environment:
             - GOPATH=/opt/gopath
             - CORE_VM_ENDPOINT=unix:///host/var/run/docker.sock
             #- CORE_LOGGING_LEVEL=DEBUG
             - CORE_LOGGING_LEVEL=INFO
             - CORE_PEER_ID=cli
             - CORE_PEER_ADDRESS=peer0.org1.example.com:7051
             - CORE_PEER_LOCALMSPID=Org1MSP
             - CORE_PEER_TLS_ENABLED=true
             - CORE_PEER_TLS_CERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.crt
             - CORE_PEER_TLS_KEY_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/server.key
             - CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
             - CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
           working_dir: /opt/gopath/src/github.com/hyperledger/fabric/peer
           command: /bin/bash
           volumes:
               - /var/run/:/host/var/run/
               - ./../chaincode/:/opt/gopath/src/github.com/chaincode
               - ./crypto-config:/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/
               - ./scripts:/opt/gopath/src/github.com/hyperledger/fabric/peer/scripts/
               - ./channel-artifacts:/opt/gopath/src/github.com/hyperledger/fabric/peer/channel-artifacts
           depends_on:
             - orderer.example.com
             - peer0.org1.example.com
             - peer1.org1.example.com
             - peer0.org2.example.com
             - peer1.org2.example.com
           networks:
             - byfn
             



   
      
    



    
    

 
   
   