

### Install & Instantiate Chaincode

    - 설치 
    peer chaincode install -n mycc -v 1.0 -p github.com/chaincode/chaincode_example02/go/
    
    peer chaincode install -n mycc -v 1.0 -l node -p /opt/gopath/src/github.com/chaincode/chaincode_example02/node/

    peer chaincode install -n mycc -v 1.0 -l java -p /opt/gopath/src/github.com/chaincode/chaincode_example02/java/


    - 초기화
    peer chaincode instantiate -o orderer.example.com:7050 --tls --cafile 
    /opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/ordererOrganizations/example.com/orderers
    /orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem -C $CHANNEL_NAME
    mycc -v 1.0 -c '{"Args":["init","a", "100", "b","200"]}' -P "AND ('Org1MSP.peer','Org2MSP.peer')"

### 