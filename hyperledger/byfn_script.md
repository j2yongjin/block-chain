


## byfn.sh 스크립트 분석

    networkUp 함수 호출
    
### networkUp

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
    
    CHANNEL_NAME="mychannel"
    # use this as the default docker-compose yaml definition
    COMPOSE_FILE=docker-compose-cli.yaml
    #
    COMPOSE_FILE_COUCH=docker-compose-couch.yaml
    # org3 docker compose file
    COMPOSE_FILE_ORG3=docker-compose-org3.yaml


### generateCerts
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

### replacePrivateKey
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
    
## docker-compose-cli


### generateChannelArtifacts

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


