## first network
    https://hyperledger-fabric.readthedocs.io/en/latest/build_network.html

### Generate Network Artifacts





    우리는 두 개의 다른 조직을 대표하는 4 명의 peer와 주문자 노드로 구성된 Hyperledger 패브릭 네트워크를 신속하게 부트 스트랩하기 위해 
    이러한 Docker 이미지를 활용하는 완전히 주석 된 스크립트 - byfn.sh를 제공합니다. 
    또한 피어를 채널에 참여시키고 체인 코드를 배포 및 인스턴스화하며 배포 된 체인 코드에 대한 트랜잭션 실행을 실행하는 
    스크립트 실행을 실행하는 컨테이너를 실행합니다
    
    Usage:
      byfn.sh <mode> [-c <channel name>] [-t <timeout>] [-d <delay>] [-f <docker-compose-file>] [-s <dbtype>] [-l <language>] [-i <imagetag>] [-v]
        <mode> - one of 'up', 'down', 'restart', 'generate' or 'upgrade'
          - 'up' - bring up the network with docker-compose up
          - 'down' - clear the network with docker-compose down
          - 'restart' - restart the network
          - 'generate' - generate required certificates and genesis block
          - 'upgrade'  - upgrade the network from v1.0.x to v1.1
        -c <channel name> - channel name to use (defaults to "mychannel")
        -t <timeout> - CLI timeout duration in seconds (defaults to 10)
        -d <delay> - delay duration in seconds (defaults to 3)
        -f <docker-compose-file> - specify which docker-compose file use (defaults to docker-compose-cli.yaml)
        -s <dbtype> - the database backend to use: goleveldb (default) or couchdb
        -l <language> - the chaincode language: golang (default), node or java
        -i <imagetag> - the tag to be used to launch the network (defaults to "latest")
        -v - verbose mode
      byfn.sh -h (print this message)
    
    Typically, one would first generate the required certificates and
    genesis block, then bring up the network. e.g.:
    
            byfn.sh generate -c mychannel
            byfn.sh up -c mychannel -s couchdb
            byfn.sh up -c mychannel -s couchdb -i 1.1.0-alpha
            byfn.sh up -l node
            byfn.sh down -c mychannel
            byfn.sh upgrade -c mychannel
    
    Taking all defaults:
            byfn.sh generate
            byfn.sh up
            byfn.sh down
            



    cd fabric-samples/first-network

    ./byfn.sh generate
    
    이 첫 번째 단계는 다양한 네트워크 엔티티에 대한 모든 인증서와 키, ordering 서비스를 부트 스트랩 (bootstrap)하는 데 
    사용되는 genesis 블록 및 채널을 구성하는 데 필요한 구성 트랜잭션 모음을 생성합니다
    
    생성 결과
    drwxrwxr-x 2 yjlee yjlee  4096 10월 22 22:56 ./
    drwxrwxr-x 7 yjlee yjlee  4096 10월 24 23:06 ../
    -rw-rw-r-- 1 yjlee yjlee     0 10월 21 11:49 .gitkeep
    -rw-r--r-- 1 yjlee yjlee   284 10월 24 23:06 Org1MSPanchors.tx
    -rw-r--r-- 1 yjlee yjlee   284 10월 24 23:06 Org2MSPanchors.tx
    -rw-r--r-- 1 yjlee yjlee   346 10월 24 23:06 channel.tx
    -rw-r--r-- 1 yjlee yjlee 12769 10월 24 23:06 genesis.block

    
### Bring Up the Network

    ./byfn.sh up
    
    node 실행
    ./byfn.sh up -l node
    
    자바 실행
    ./byfn.sh up -l java
    
### Bring Down the Network
    
    ./byfn.sh down
    
    기본 tooling 및 부트 스트랩 메커니즘에 대해 더 자세히 알고 싶으면 계속 읽으십시오. 
    다음 섹션에서는 완벽한 기능을 갖춘 Hyperledger 패브릭 네트워크를 구축하기위한 다양한 단계와 요구 사항을 살펴 보겠습니다
    
    아래에 설명 된 수동 단계에서는 cli 컨테이너의 CORE_LOGGING_LEVEL이 DEBUG 로 설정되어 있다고 가정합니다. 
    첫 번째 네트워크 디렉토리의 docker-compose-cli.yaml 파일을 수정하여이를 설정할 수 있습니다. 예
    
    cli:
      container_name: cli
      image: hyperledger/fabric-tools:$IMAGE_TAG
      tty: true
      stdin_open: true
      environment:
        - GOPATH=/opt/gopath
        - CORE_VM_ENDPOINT=unix:///host/var/run/docker.sock
        - CORE_LOGGING_LEVEL=DEBUG
        #- CORE_LOGGING_LEVEL=INFO
        
### Crypto Generator

    cryptogen 도구를 사용하여 다양한 네트워크 엔터티에 대한 암호 자료 (x509 인증서 및 서명 키)를 생성합니다. 
    이 인증서는 신원을 나타내며 당사 엔티티가 통신하고 거래 할 때 서명 / 확인 인증을 수행 할 수 있습니다
    
### How does it work?
    
    Cryptogen은 네트워크 토폴로지를 포함하고있는 crypto-config.yaml 파일을 사용하며 조직과 해당 조직에 속한 
    구성 요소 모두에 대한 인증서와 키 세트를 생성 할 수 있습니다. 각 조직에는 특정 구성 요소 (peer 및 order)를 
    해당 조직에 바인딩하는 고유 루트 인증서 (ca-cert)가 제공됩니다. 
    각 조직에 고유 한 CA 인증서를 할당하여 
    참여하는 회원이 자체 인증 기관을 사용하는 일반적인 네트워크를 모방합니다. 
    Hyperledger Fabric 내의 트랜잭션 및 통신은 엔티티의 private key (key store)에 의해 
    서명 된 다음 public key (signcerts)를 통해 검증됩니다.
    
    이 파일 내에 count 변수가 있음을 알 수 있습니다. 우리는이를 사용하여 조직 당 피어의 수를 지정합니다. 
    우리의 경우 Org 당 두 명의 동료가 있습니다. 
    우리는 지금 x.509 인증서 및 공개 키 인프라의 세부 사항을 조사하지 않을 것입니다. 관심이 있다면, 
    당신은이 주제들을 당신의 시간에 정독 할 수 있습니다.
    
    이 도구를 실행하기 전에 crypto-config.yaml의 스 니펫을 간단히 살펴 보겠습니다. 
    OrdererOrgs 아래의 "Name", "Domain"및 "Specs"매개 변수에 특히주의하십시오
    
    네트워크 엔터티의 명명 규칙은 "{{.Hostname}}. {{. Domain}}"입니다. 따라서 순서 지정 노드를 참조 점으로 사용하여 Orderer의 MSP ID에 연결된 
    orderer.example.com이라는 주문 노드가 남습니다. 
    이 파일에는 정의 및 구문에 대한 광범위한 설명서가 들어 있습니다. MSP에 관한 더 자세한 정보는 회원 서비스 제공 업체 (MSP) 문서를 참조하십시오.
    
    cryptogen 도구를 실행하면 생성 된 인증서와 키가 제목이 지정된 폴더에 저장됩니다
    
### Configuration Transaction Generator

    configtxgen 도구는 네 개의 구성 아티팩트를 만드는 데 사용됩니다
    
    •orderer genesis block,
    •channel configuration transaction,
    •and two anchor peer transactions - one for each Peer Org.

    주문자 블록은 주문 서비스를위한 Genesis Block이며 채널 구성 트랜잭션 파일은 채널 생성시 주문자에게 방송됩니다. 
    앵커 피어 트랜잭션은 이름에서 알 수 있듯이이 채널에서 각 조직의 앵커 피어를 지정합니다

### How does it work?

    Configtxgen은 샘플 네트워크의 정의가 들어있는 파일 (configtx.yaml)을 사용합니다. 
    각 피어 노드를 관리하고 유지 관리하는 Orderer Org (Orderer Org)와 Peer Orgs (Org1 & Org2)의 세 멤버가 있습니다
    
    이 파일은 또한 두 개의 Peer Orgs로 구성된 컨소시엄 - SampleConsortium을 지정합니다. 
    이 파일 맨 위에있는 "Profiles"섹션에 특히주의하십시오. 두 개의 고유 한 헤더가 있음을 알 수 있습니다. 
    주문자 창세기 - TwoOrgsOrdererGenesis 용 - 우리 채널 용 - TwoOrgsChannel.
    
    이 헤더는 중요합니다. 우리가 이슈를 생성 할 때 인수로 전달합니다
    
    SampleConsortium은 시스템 레벨 프로필에 정의 된 다음 채널 레벨 프로필에서 참조됩니다. 
    채널은 컨소시엄의 범위 내에 존재하며, 모든 컨소시엄은 네트워크의 범위 내에서 광범위하게 정의되어야합니다
    
    이 파일에는 주목할 가치가있는 두 가지 추가 사양이 포함되어 있습니다. 첫째, 각 피어 조직 
    (peer0.org1.example.com 및 peer0.org2.example.com)에 대한 앵커 피어를 지정합니다. 
    두 번째로, 우리는 각 구성원에 대한 MSP 디렉토리의 위치를 ​​가리키며 차례로 발주자 
    생성 블록에 각 Org에 대한 루트 인증서를 저장할 수 있습니다. 이것은 중요한 개념입니다. 
    
    이제 주문 서비스와 통신하는 모든 네트워크 엔터티는 디지털 서명을 확인할 수 있습니다
    
### Run the tools

    configtxgen 및 cryptogen 명령을 사용하여 인증서 / 키 및 다양한 구성 아티팩트를 수동으로 생성 할 수 있습니다. 
    또는, byfn.sh 스크립트를 수정하여 목표를 달성 할 수도 있습니다
    
### Manually generate the artifacts

    byfn.sh 스크립트의 generateCerts 함수를 참조하여 crypto-config.yaml 파일에 정의 된대로 
    네트워크 구성에 사용될 인증서를 생성하는 데 필요한 명령을 참조 할 수 있습니다. 그러나 편의를 위해 여기서도 참조 할 것입니다
    
    우선 cryptogen 도구를 실행 해 봅시다. 우리의 바이너리는 bin 디렉토리에 있으므로 도구가 상주하는 상대 경로를 제공해야한다
    
    ../bin/cryptogen generate --config=./crypto-config.yaml
    
    result
    
    org1.example.com
    org2.example.com
    
    인증서 및 키 (즉, MSP 자료)는 첫 번째 네트워크 디렉토리의 루트에있는 crypto-config 디렉토리로 출력됩니다
    
    다음으로, 우리는 configtxgen 툴에게 인제 스트 될 필요가있는 configtx.yaml 파일을 찾을 곳을 알려줄 필요가있다. 
    우리는 현재의 작업 디렉토리를 살펴볼 것입니다
    
    export FABRIC_CFG_PATH=$PWD
    
    그런 다음 configtxgen 도구를 호출하여 orderer genesis 블록을 만듭니다
    
    ../bin/configtxgen -profile TwoOrgsOrdererGenesis -channelID byfn-sys-channel -outputBlock ./channel-artifacts/genesis.block


### Create a Channel Configuration Transaction

    다음에는 채널 트랜잭션 이슈를 생성해야합니다. 이 지침에서 사용할 수있는 환경 변수로 $ CHANNEL_NAME을 바꾸거나 CHANNEL_NAME을 (를) 설정해야합니다.
    
    export CHANNEL_NAME=mychannel  && ../bin/configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./channel-artifacts/channel.tx -channelID $CHANNEL_NAME
    
    다음으로 우리가 구성 할 채널에서 Org1의 앵커 피어를 정의합니다. 
    다시 말하지만 $ CHANNEL_NAME을 바꾸거나 다음 명령에 대한 환경 변수를 설정하십시오. 
    터미널 출력은 채널 트랜잭션 아티팩트의 출력을 모방합니다
    
    ../bin/configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/Org1MSPanchors.tx -channelID $CHANNEL_NAME -asOrg Org1MSP
    
    ../bin/configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./channel-artifacts/Org2MSPanchors.tx -channelID $CHANNEL_NAME -asOrg Org2MSP
    
### Start the network

    스크립트를 활용하여 네트워크를 확대 할 것입니다. 도커 작성 파일은 
    이전에 다운로드 한 이미지를 참조하고 이전에 생성 된 것으로 주문자를 부트 스트랩합니다
    
    각 호출의 구문과 기능을 노출하기 위해 수동으로 명령을 수행하려고합니다
    
    docker-compose -f docker-compose-cli.yaml up -d
    
    네트워크의 실시간 로그를 보려면 -d 플래그를 제공하지 마십시오. 
    로그 스트림을 보내려면 CLI 호출을 실행하기 위해 두 번째 터미널을 열어야합니다
    
### Environment variables

    peer0.org1.example.com에 대한 다음 CLI 명령이 작동하려면 아래에 나와있는 네 가지 환경 변수로 명령을 시작해야합니다
    
    peer0.org1.example.com에 대한 이러한 변수는 CLI 컨테이너로 구워 지므로 전달하지 않고 조작 할 수 있습니다. 
    그러나 다른 피어 또는 주문자에게 전화를 보내려는 경우 컨테이너를 시작하기 전에 
    docker-compose-base.yaml을 편집하여이 값을 적절하게 제공 할 수 있습니다. 
    다른 피어 및 조직을 사용하도록 다음 네 가지 환경 변수를 수정하십시오.
    
    # Environment variables for PEER0
    
    CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
    CORE_PEER_ADDRESS=peer0.org1.example.com:7051
    CORE_PEER_LOCALMSPID="Org1MSP"
    CORE_PEER_TLS_ROOTCERT_FILE=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations
    
### Create & Join Channel

    docker exec -it cli bash

