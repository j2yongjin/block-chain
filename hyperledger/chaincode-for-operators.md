## Chaincode for Operators

### What is Chaincode?
    
    Chaincode는 Go, node.js 또는 Java로 작성된 프로그램으로, 규정 된 인터페이스를 구현합니다. 
    체인 코드는 엔도 싱 피어 프로세스에서 격리 된 보안 Docker 컨테이너에서 실행됩니다. 
    Chaincode는 응용 프로그램에서 제출 한 트랜잭션을 통해 원장 상태를 초기화하고 관리합니다.
    
    체인 코드는 일반적으로 네트워크 구성원이 동의 한 비즈니스 논리를 처리하므로 
    "smart contract"으로 간주 될 수 있습니다. 
    체인 코드에 의해 생성 된 상태는 해당 체인 코드로만 범위가 지정되며 
    다른 체인 코드로 직접 액세스 할 수 없습니다. 
    그러나, 동일한 네트워크 내에서, 적절한 허가가 주어진다면, 
    체인 코드는 다른 체인 코드를 호출하여 그 상태에 액세스 할 수있다
    
    다음 섹션에서는 블록 체인 네트워크 운영자 인 노아의 시각을 통해 체인 코드를 살펴 보겠습니다. 
    Noah의 이익을 위해 우리는 체인 코드 라이프 사이클 운영에 중점을 둘 것입니다. 
    (블록 체인 네트워크 내에서 체인 코드의 운영 라이프 사이클의 함수로 체인 코드를 packaging, installing
    , 인스턴스화 및 업그레이드하는 프로세스)
    
    
### Chaincode lifecycle

    Hyperledger 패브릭 API는 peer, orderer 및 MSP와 같은 블록 체인 네트워크의 다양한 노드와의 
    상호 작용을 가능하게하며 또한 인증 피어 노드에 체인 코드를 패키징, 설치, 
    인스턴스화 및 업그레이드 할 수있게합니다
    
    Hyperledger Fabric 언어 별 SDK는 Hypercode Fabric API의 특성을 추상화하여 응용 프로그램 개발을 
    용이하게하지만 체인 코드의 수명주기를 관리하는 데 사용할 수 있습니다. 
    또한 Hyperledger Fabric API는 CLI를 통해 직접 액세스 할 수 있으며이 문서에서 사용하게 될 것입니다
    
    우리는 체인 코드의 라이프 사이클 (package, install,  instantiate 및 upgrade)을 관리하기위한 
    네 가지 명령을 제공합니다. 
    향후 릴리스에서는 실제로 중지하지 않고 체인 코드를 비활성화했다가 다시 사용하도록 중지 및 
    시작 트랜잭션을 추가하는 것을 고려하고 있습니다. 
    체인 코드가 성공적으로 설치되고 인스턴스화 된 후에는 체인 코드가 활성화되어 
    (실행 중) 호출 트랜잭션을 통해 트랜잭션을 처리 할 수 ​​있습니다. 
    체인 코드는 설치 후 언제든지 업그레이드 할 수 있습니다.
    
### Packaging

    체인 코드 패키지는 3 부분으로 구성됩니다.
    
     1)ChaincodeDeploymentSpec 또는 CDS에 정의 된 체인 코드. CDS는 체인 코드 패키지를 코드 및 이름 및 버전과 같은 기타 속성으로 정의합니다.
     
     2)승인을 위해 사용 된 것과 동일한 정책으로 구문 적으로 설명 될 수 있고 
     승인 정책(Endorsement policies) 에 설명되어있는 선택적 인스턴스 정책.
     
     3)체인 코드를 "소유하고있는"엔티티에 의한 일련의 서명.
     
     
     서명의 목적은 다음과 같습니다.
     
     1) 체인 코드의 소유권을 확립하는 것
     2) 패키지의 내용을 확인할 수 있도록     
     3) 패키지 탬 퍼링을 탐지 할 수 있도록
     
     채널의 체인 코드 인스턴스 생성 트랜잭션 생성자는 
     체인 코드의 인스턴스화 정책에 대해 유효성이 검사됩니다.
 
 ### Creating the package
 
    체인 코드를 패키징하는 데는 두 가지 방법이 있습니다. 
    하나는 체인 코드의 소유자를 여러 명 갖고 싶어하므로 여러 ID로 서명 된 chaincode 패키지가 있어야합니다.
     이 작업 과정을 수행하려면 처음에는 서명 된 체인 코드 패키지 (SignedCDS)를 만들어야하며, 
    이후 서명 된 다른 소유자 각각에게 연속적으로 전달됩니다
    
    더 간단한 워크 플로우는 설치 트랜잭션을 발행하는 
    노드의 ID의 서명 만 가진 SignedCDS를 배치 할 때 사용됩니다
    
    먼저 복잡한 사례를 다룰 것입니다. 
    그러나 아직 여러 소유자를 걱정할 필요가없는 경우 아래 체인 코드 설치 섹션으로 건너 뛸 수 있습니다.
    
    서명 된 체인 코드 패키지를 만들려면 다음 명령을 사용하십시오
    
    peer chaincode package -n mycc -p github.com/hyperledger/fabric/examples/chaincode/go/example02/cmd -v 0 -s -S -i "AND('OrgA.admin')" ccpack.out
    
    -s 옵션은 단순히 원시 CDS를 작성하는 것이 아니라 여러 소유자가 서명 할 수있는 패키지를 작성합니다. -s가 지정된 경우 다른 소유자가 서명해야하는 경우 -S 옵션도 지정해야합니다. 그렇지 않으면, 프로세스는 CDS뿐만 아니라 인스턴스화 정책 만 포함하는 SignedCDS를 작성합니다
        
    -S 옵션은 프로세스가 core.yaml의 localMspid 등록 정보 값으로 식별되는 MSP를 사용하여 패키지에 서명하도록 지시합니다.

    -S 옵션은 선택적입니다. 그러나 패키지가 서명없이 만들어지면 signpackage 명령을 사용하여 다른 소유자가 서명 할 수 없습니다
    
    선택적 -i 옵션은 체인 코드에 대한 인스턴스화 정책을 지정할 수있게합니다. 인스턴스화 정책은 승인 정책과 동일한 형식을 가지며 체인 코드를 인스턴스화 할 수있는 ID를 지정합니다. 위의 예에서 OrgA의 관리자 만이 chaincode를 인스턴스화 할 수 있습니다. 정책이 제공되지 않으면 기본 정책이 사용되며 피어의 MSP의 관리자 ID 만 체인 코드를 인스턴스화 할 수 있습니다
    
### Package signing

    작성시 서명 된 체인 코드 패키지는 검사 및 서명을 위해 다른 소유자에게 양도 될 수 있습니다. 워크 플로는 체인 코드 패키지의 대역 외 서명을 지원합니다.
    
    ChaincodeDeploymentSpec은 선택적으로 SignedChaincodeDeploymentSpec (또는 SignedCDS)을 작성하기 위해 집단 소유자가 서명 할 수 있습니다. SignedCDS는 3 가지 요소를 포함합니다 :
        
    1. CDS는 소스 코드, 이름 및 체인 코드의 버전을 포함합니다.
    2. 보증 정책으로 표현 된 체인 코드의 인스턴스화 정책.
    3. 보증서를 통해 정의 된 체인 코드 소유자 목록
    
    
    이 인증 정책은 일부 채널에서 체인 코드가 인스턴스화 될 때 적절한 
    MSP 보안 주체를 제공하기 위해 대역 외로 결정됩니다. 인스턴스화 정책이 지정되지 않은 경우 
    기본 정책은 채널의 모든 MSP 관리자입니다.
    
    이 인증 정책은 MSP 보안을 제공하기 위해 제공됩니다. default 정책은 모든 MSP 관리자가 있습니다.
    
    체인 코드 소유자는 다음 명령을 사용하여 이전에 작성한 서명 된 패키지에 서명 할 수 있습니다
    
    peer chaincode signpackage ccpack.out signedccpack.out
    
    여기서 ccpack.out 및 signedccpack.out은 각각 입력 및 출력 패키지입니다. signedccpack.out에는 
    로컬 MSP를 사용하여 서명 된 패키지에 대한 추가 서명이 들어 있습니다.
    
### Installing chaincode

    
    설치 트랜잭션은 체인 코드의 소스 코드를 ChaincodeDeploymentSpec 
    (또는 CDS)이라는 규정 된 형식으로 패키징하고 해당 체인 코드를 실행할 피어 노드에 설치합니다
    
    체인 코드를 실행할 채널의 각 인증 피어 노드에 체인 코드를 설치해야합니다
    
    설치 API에 단순히 ChaincodeDeploymentSpec이 지정되면 인스턴스화 정책이 기본값으로 설정되고 빈 소유자 목록이 포함됩니다
    
    체인 코드는 체인 코드 소유 구성원의 피어 노드를 승인하는 경우에만 설치해야 
    네트워크의 다른 구성원으로부터 체인 코드 논리의 기밀성을 보호 할 수 있습니다. 
    체인 코드가없는 멤버는 체인 코드의 트랜잭션을 승인 할 수 없습니다. 
    즉, 체인 코드를 실행할 수 없습니다. 
    그러나 이들은 여전히 ​​장부에 대한 유효성을 확인하고 트랜잭션을 커밋 할 수 있습니다
    
    
    체인 코드를 설치하려면 시스템 체인 코드 섹션에 설명 된 LSCC (라이프 사이클 시스템 체인 코드)에 
    SignedProposal을 보냅니다
    
    
    예를 들어, CLI를 사용하여 Simple Asset Chaincode 섹션에 설명 된 
    sacc 샘플 체인 코드를 설치하려면 명령이 다음과 같아야합니다
    
    peer chaincode install -n asset_mgmt -v 1.0 -p sacc
    
    CLI는 내부적으로 sacc에 대한 SignedChaincodeDeploymentSpec을 생성하고이를 
    LSCC에서 Install 메소드를 호출하는 로컬 피어로 보냅니다. 
    -p 옵션의 인수는 사용자의 GOPATH의 소스 트리 내에 있어야하는 체인 코드의 경로를 지정합니다 
    (예 : $ GOPATH / src / sacc. 명령 옵션에 대한 자세한 설명은 CLI 절을 참조하십시오.
    
    피어에 설치하려면 SignedProposal의 서명이 피어의 로컬 MSP 관리자 중 한 명이어야합니다.
    
### Instantiate

    인스턴스화 트랜잭션은 LSCC (Life Cycle System Chaincode)를 호출하여 
    채널의 체인 코드를 만들고 초기화합니다. 
    이것은 체인 코드 채널 바인딩 프로세스입니다. 
    체인 코드는 여러 채널에 바인딩되어 각 채널에서 개별적으로 독립적으로 작동 할 수 있습니다. 
    즉, 체인 코드를 설치하고 인스턴스화 할 수있는 
    다른 채널 수에 관계없이 상태는 트랜잭션이 제출 된 채널과 격리되어 있습니다.
    
    인스턴스 생성 트랜잭션의 작성자는 SignedCDS에 포함 된 체인 코드의 인스턴스화 정책을 충족해야하며 
    채널 작성자로 채널의 일부로 구성되어야합니다. 
    악의적 인 엔티티가 체인 코드를 배포하거나 구성원을 속여 언 바운드 채널에서 
    체인 코드를 실행하는 것을 방지하려면 채널 보안에 중요합니다.
    
    예를 들어 기본 인스턴스화 정책은 모든 채널 MSP 관리자이므로 
    체인 코드 인스턴스 생성 트랜잭션 작성자는 채널 관리자의 구성원이어야합니다. 
    트랜잭션 제안서가 엔도 서에 도착하면 생성자의 서명을 인스턴스화 정책과 비교하여 검증합니다. 
    이 작업은 장부에 커밋하기 전에 트랜잭션 유효성 검사 중에 다시 수행됩니다.
    
    또한 인스턴스 생성 트랜잭션은 채널에서 해당 체인 코드에 대한 보증 정책을 설정합니다. 
    보증 정책은 채널 구성원이 승인 한 거래 결과에 대한 증명 요구 사항을 설명합니다
    
    예를 들어 CLI를 사용하여 sacc chaincode를 인스턴스화하고 
    john 및 0으로 상태를 초기화하면 명령은 다음과 같이 표시됩니다
    
    peer chaincode instantiate -n sacc -v 1.0 -c '{"Args":["john","0"]}' -P "AND ('Org1.member','Org2.member')"
    
    보증 정책 (CLI는 폴란드어 표기법을 사용함)에 유의하십시오.이 경우 모든 트랜잭션을 
    sacc로 처리하려면 Org1 및 Org2 구성원의 보증이 필요합니다. 
    즉, Org1과 Org2 모두 유효한 트랜잭션이되도록 sacc에서 Invoke를 실행 한 결과에 서명해야합니다
    
    성공적으로 인스턴스화 된 후에는 체인 코드가 채널의 활성 상태가되며 
    ENDORSER_TRANSACTION 유형의 모든 트랜잭션 제안을 처리 할 준비가됩니다. 
    트랜잭션은 인증 피어에 도착하면서 동시에 처리됩니다.
    
### Upgrade
    
    체인 코드는 SignedCDS의 일부인 버전을 변경하여 언제든지 업그레이드 할 수 있습니다. 
    소유자 및 인스턴스 정책과 같은 다른 부분은 선택 사항입니다. 
    그러나 체인 코드 이름은 동일해야합니다. 그렇지 않으면 완전히 다른 체인 코드로 간주됩니다
    
    업그레이드하기 전에 새 버전의 체인 코드를 필요한 엔도 서에 설치해야합니다. 
    업그레이드는 체인 코드의 새 버전을 채널에 바인드하는 인스턴스화 트랜잭션과 유사한 트랜잭션입니다. 체인 코드의 이전 버전에 바인딩 된 다른 채널은 여전히 ​​이전 버전과 함께 실행됩니다. 즉, 업그레이드 트랜잭션은 한 번에 하나의 채널, 트랜잭션이 제출되는 채널에만 영향을줍니다
    
    체인 코드의 여러 버전이 동시에 활성화 될 수 있으므로 업그레이드 프로세스가 자동으로 이전 버전을 제거하지 않으므로 사용자는 당분간이를 관리해야합니다.
    
    인스턴스화 트랜잭션에는 미묘한 차이점이 있습니다. 업그레이드 트랜잭션은 새 정책 (지정된 경우)이 아닌 현재 체인 코드 인스턴스화 정책과 비교하여 검사됩니다. 이것은 현재 인스턴스화 정책에 지정된 기존 구성원 만이 체인 코드를 업그레이드 할 수 있도록하기위한 것입니다
    
    업그레이드하는 동안 chaincode Init 함수가 호출되어 데이터 관련 업데이트를 수행하거나 다시 초기화하므로 체인 코드를 업그레이드 할 때 상태를 다시 설정하지 않도록주의해야합니다
    
### Stop and Start
   