## first network
    https://hyperledger-fabric.readthedocs.io/en/latest/build_network.html

### Generate Network Artifacts

    cd fabric-samples/first-network

    ./byfn.sh generate
    
    이 첫 번째 단계는 다양한 네트워크 엔티티에 대한 모든 인증서와 키, 
    ordering 서비스를 부트 스트랩 (bootstrap)하는 데 
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