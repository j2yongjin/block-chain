## Using CouchDB

    The tutorial will take you through the following steps:
    1.Enable CouchDB in Hyperledger Fabric
    2.Create an index
    3.Add the index to your chaincode folder
    4.Install and instantiate the Chaincode
    5.Query the CouchDB State Database
    6.Query the CouchDB State Database With Pagination
    7.Update an Index
    8.Delete an Index
    
    CouchDB에 대한 자세한 내용은 CouchDB를 상태 데이터베이스로 참조하고 
    원장 관리자에 대한 자세한 내용은 원장 주제를 참조하십시오. 
    Blockchain 네트워크에서 CouchDB를 활용하는 방법에 대한 자세한 내용은 아래의 자습서를 따르십시오.
    
    이 튜토리얼에서는 Marbles 샘플을 사용 사례로 사용하여 CouchDB를 Fabric과 함께 
    사용하는 방법을 시연하고 Marble을 Building Your First Network (BYFN) 자습서 네트워크에 배포합니다. 샘플, 바이너리 및 고정 이미지 설치 작업을 완료해야합니다. 그러나 BYFN 자습서를 
    실행하는 것은이 자습서의 필수 조건은 아니며 대신이 자습서에서 네트워크를 사용하는 데 필요한 명령이 제공됩니다

### Why CouchDB?

    Fabric은 두 가지 유형의 피어 데이터베이스를 지원합니다. 
    LevelDB는 피어 노드에 내장 된 기본 상태 데이터베이스이며 간단한 키 - 값 쌍으로 
    체인 코드 데이터를 저장하고 키, 키 범위 및 복합 키 쿼리 만 지원합니다. 
    
    CouchDB는 chaincode 데이터 값을 JSON으로 모델링 할 때 
    다양한 쿼리를 지원하는 선택적 대체 상태 데이터베이스입니다. 
    리치 쿼리는 키가 아닌 실제 데이터 값의 내용을 쿼리 할 때보다 유연하고 
    효율적으로 대형 인덱스 데이터 저장소에 효율적입니다. 
    CouchDB는 순수 키 - 값 저장소가 아닌 JSON 문서 데이터 저장소이므로 
    데이터베이스의 문서 내용에 대한 색인을 생성 할 수 있습니다
    
    콘텐츠 기반 JSON 쿼리와 같은 CouchDB의 이점을 활용하려면 데이터를 
    JSON 형식으로 모델링해야합니다. 네트워크를 설정하기 전에 LevelDB 또는 
    CouchDB를 사용할지를 결정해야합니다. 
    데이터 호환성 문제로 인해 피어를 LevelDB에서 CouchDB로 전환 할 수 없습니다. 
    네트워크의 모든 피어는 동일한 데이터베이스 유형을 사용해야합니다. 
    JSON과 바이너리 데이터 값이 혼합되어있는 경우에도 
    CouchDB를 사용할 수 있지만 이진 값은 키, 키 범위 및 복합 키 쿼리를 기반으로 만 쿼리 할 수 ​​있습니다.
    
### Enable CouchDB in Hyperledger Fabric
    
    CouchDB는 피어와 함께 별도의 데이터베이스 프로세스로 실행되므로 설정, 관리 및 작업과 관련하여 추가 고려 사항이 있습니다. 
    CouchDB의 도커 이미지를 사용할 수 있으며 피어와 동일한 서버에서 실행하는 것이 좋습니다
   
    피어 당 하나의 CouchDB 컨테이너를 설정하고 core.yaml에있는 구성을 CouchDB 컨테이너를 가리 키도록 변경하여 각 피어 컨테이너를 업데이트해야합니다. 
    core.yaml 파일은 환경 변수 FABRIC_CFG_PATH에 지정된 디렉토리에 있어야합니다.
    
    - 도커 배포의 경우 core.yaml은 사전 구성되어 피어 컨테이너 FABRIC_CFG_PATH 폴더에 있습니다. 
    그러나 도커 환경을 사용할 때는 일반적으로 docker-compose-couch.yaml을 편집하여 
    core.yaml을 무시하고 환경 변수를 전달합니다
    
    - 기본 이진 배포의 경우 core.yaml은 릴리스 아티팩트 배포에 포함됩니다
    
    core.yaml의 stateDatabase 섹션을 편집하십시오. CouchDB를 stateDatabase로 지정하고 연관된 
    couchDBConfig 특성을 입력하십시오. CouchDB가 패브릭과 함께 작동하도록 구성하는 것에 대한 자세한 내용은 여기를 참조하십시오. 
    CouchDB 용으로 구성된 core.yaml 파일의 예제를 보려면 HyperLedger 
    / fabric-samples / first-network 디렉토리에서 BYFN docker-compose-couch.yaml을 검사하십시오
    
### Create an index
    인덱스가 중요한 이유?
    
    인덱스를 사용하면 모든 쿼리가있는 모든 행을 검사하지 않고도 
    데이터베이스를 쿼리 할 수 ​​있으므로 더 빠르고 효율적으로 실행할 수 있습니다. 
    일반적으로 색인은 자주 발생하는 쿼리 기준에 맞게 작성되므로 데이터를보다 효율적으로 쿼리 할 수 ​​있습니다. CouchDB의 주요 이점, 즉 JSON 데이터에 대해 풍부한 쿼리를 수행하는 기능을 활용하려면 인덱스가 필요하지 않지만 성능을 위해 강력히 권장됩니다. 
    또한, 조회에서 정렬이 필요하면 CouchDB는 정렬 된 필드의 색인을 필요로합니다
    
    인덱스가없는 리치 쿼리는 작동하지만 CouchDB 로그에 인덱스를 찾을 수 없다는 경고를 던질 수 있습니다. 그러나 서식있는 쿼리에 정렬 지정이 포함되어 있으면 해당 필드의 인덱스가 필요합니다. 
    그렇지 않으면 쿼리가 실패하고 오류가 발생합니다.
    
    인덱스 구축을 시연하기 위해 Marbles 샘플의 데이터를 사용합니다. 이 예에서, 구슬 데이터 구조는 다음과 같이 정의됩니다.
    
    type marble struct {
             ObjectType string `json:"docType"` //docType is used to distinguish the various types of objects in state database
             Name       string `json:"name"`    //the field tags are needed to keep case from bouncing around
             Color      string `json:"color"`
             Size       int    `json:"size"`
             Owner      string `json:"owner"`
    }

    이 구조에서 속성 (docType, name, color, size, owner)은 자산과 연결된 원장 데이터를 정의합니다. 
    docType 속성은 체인 코드에서 별도로 쿼리해야 할 수도있는 여러 데이터 유형을 구분하는 데 사용되는
     패턴입니다. CouchDB를 사용할 때이 docType 속성을 포함시켜 체인 코드 네임 스페이스에있는 
     각 문서 유형을 구별하는 것이 좋습니다. 각 체인 코드는 자체 CouchDB 데이터베이스로 표현됩니다. 
     즉, 각 체인 코드에는 키에 대한 자체 네임 스페이스가 있습니다

    Marbles 데이터 구조와 관련하여 docType은이 문서 / 자산이 marble 자산임을 식별하는 데 사용됩니다
    . 잠재적으로 다른 문서 / 자산이 체인 코드 데이터베이스에있을 수 있습니다. 
    데이터베이스에서 문서는 모든 속성 값에 대해 검색 할 수 있습니다.
    
    체인 코드 쿼리에 사용할 인덱스를 정의 할 때 각 인덱스는 확장자가 * .json 인 자체 텍스트 파일에 정의되어야하며 
    인덱스 정의는 CouchDB 인덱스 JSON 형식으로 형식화되어야합니다
    
    To define an index, three pieces of information are required:
    
    •fields: these are the frequently queried fields
    •name: name of the index
    •type: always json in this context
    
    For example, a simple index named foo-index for a field named foo.
    {
        "index": {
            "fields": ["foo"]
        },
        "name" : "foo-index",
        "type" : "json"
    }
    
    선택적으로, ddoc 디자인 문 서 속성이 색인 정의에 지정 될 수 있습니다. 
    설계 문서는 인덱스를 포함하도록 설계된 CouchDB 구조이다. 효율성을 위해 인덱스를 
    디자인 문서로 그룹화 할 수 있지만 CouchDB는 디자인 문서 당 하나의 인덱스를 권장합니다
    
    인덱스를 정의 할 때 인덱스 이름과 함께 ddoc 속성 및 값을 포함시키는 것이 좋습니다. 필요한 경우 나중에 색인을 갱신 할 수 있도록이 속성을 포함시키는 것이 중요합니다. 
    또한 쿼리에 사용할 인덱스를 명시 적으로 지정할 수있는 기능을 제공합니다
    
    다음은 여러 필드 (docType 및 owner)를 사용하는 인덱스 이름 indexOwner가있는 
    Marbles 샘플의 인덱스 정의의 또 다른 예이며 ddoc 속성을 포함합니다
    {
      "index":{
          "fields":["docType","owner"] // Names of the fields to be queried
      },
      "ddoc":"indexOwnerDoc", // (optional) Name of the design document in which the index will be created.
      "name":"indexOwner",
      "type":"json"
    }

    {
      "index":{
          "fields":["owner"] // Names of the fields to be queried
      },
      "ddoc":"index1Doc", // (optional) Name of the design document in which the index will be created.
      "name":"index1",
      "type":"json"
    }
    
    {
      "index":{
          "fields":["owner", "color"] // Names of the fields to be queried
      },
      "ddoc":"index2Doc", // (optional) Name of the design document in which the index will be created.
      "name":"index2",
      "type":"json"
    }
    
    {
      "index":{
          "fields":["owner", "color", "size"] // Names of the fields to be queried
      },
      "ddoc":"index3Doc", // (optional) Name of the design document in which the index will be created.
      "name":"index3",
      "type":"json"
    }

### Add the index to your chaincode folder

    일단 색인을 완성하면 적절한 메타 데이터 폴더에 배치되어 배포 용 체인 코드와 함께 패키지 될 준비가됩니다
    
    체인 코드 설치 및 인스턴스화에서 Hyperledger Fabric Node SDK를 사용하는 경우 
    JSON 인덱스 파일은이 디렉토리 구조를 준수하는 한 모든 폴더에 위치 할 수 있습니다.
     client.installChaincode () API를 사용하여 체인 코드를 설치하는 동안 
     설치 요청에 특성 (metadataPath)을 포함시킵니다. metadataPath의 
    값은 JSON 색인 파일을 포함하는 디렉토리 구조에 대한 절대 경로를 나타내는 문자열입니다
    
    또는 피어 명령을 사용하여 체인 코드를 설치하고 인스턴스화하는 경우 
    JSON 색인 파일은 체인 코드가있는 디렉토리 내에있는 META-INF / statedb / couchdb / indexes 
    경로 아래에 있어야합니다
    
    아래의 Marbles 샘플은 피어 명령을 사용하여 설치 될 체인 코드와 함께 인덱스가 패키지되는 방법을 보여줍니다
    
### Start the network

    cd fabric-samples/first-network
    ./byfn.sh down
    
    ./byfn.sh up -c mychannel -s couchdb
    이렇게하면 두 개의 조직 (두 개의 피어 노드를 유지 관리하는)과 CouchDB를 상태 데이터베이스로 사용하는 주문 서비스가있는 mychannel이라는 단일 채널로 구성된 간단한 패브릭 네트워크가 생성됩니다
    
### Install and instantiate the Chaincode
     
     클라이언트 애플리케이션은 체인 코드를 통해 블록 체인 원장과 상호 작용합니다. 따라서 트랜잭션을 실행하고 보증하고 채널에서 체인 코드를 인스턴스화하는 모든 피어에 체인 코드를 설치해야합니다. 이전 섹션에서는 배포 할 준비가 될 수 있도록 체인 코드를 패키지하는 방법을 설명했습니다.
     
     체인 코드는 피어에 설치되고 피어 명령을 사용하여 채널에 인스턴스화됩니다.
     
     docker exec -it cli bash
     
     peer chaincode install -n marbles -v 1.0 -p github.com/chaincode/marbles02/go
     
     export CHANNEL_NAME=mychannel
     peer chaincode instantiate -o orderer.example.com:7050 --tls --cafile /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C $CHANNEL_NAME -n marbles -v 1.0 -c '{"Args":["init"]}' -P "OR ('Org0MSP.peer','Org1MSP.peer')"
     
### Verify index was deployed
  
    docker logs peer0.org1.example.com  2>&1 | grep "CouchDB index"
  
      result 
      [couchdb] CreateIndex -> INFO 0be Created CouchDB index [indexOwner] in state database [mychannel
  
    Marbles가 BYFN 피어 peer0.org1.example.com에 설치되어 있지 않으면 Marbles가 설치된 다른 피어의 이름으로 바꿔야 할 수도 있습니다
    
    
    --- 미완성 ---


     





    
    
    
    
    
