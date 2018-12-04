package com.daou.supplier.config;

/**
 * Created by yjlee on 2018-10-28.
 */
public class SupplierConfig {

    public static final String ORG1_MSP = "Org1MSP";
    public static final String ORG1 = "org1";
    public static final String ORG2_MSP = "Org2MSP";
    public static final String ORG2 = "org2";

    public static final String CA_ORG1_URL = "http://localhost:7054";
    public static final String ORDERER_URL = "grpc://localhost:7050";

    public static final String ORDERER_NAME = "orderer.chainbooks.com";
    public static final String CHANNEL_NAME = "mychannel";

    public static final String ORG1_PEER_0 = "peer0.org1.chainbooks.com";
    public static final String ORG1_PEER_0_URL = "grpc://127.0.0.1:7051";
    public static final String ORG2_PEER_0 = "peer0.org2.chainbooks.com";
    public static final String ORG2_PEER_0_URL = "grpc://localhost:8051";

//    public static final String ORG1_PEER_1 = "peer1.org1.chainbooks.com";
//    public static final String ORG1_PEER_1_URL = "grpc://127.0.0.1:7051";
//    public static final String ORG2_PEER_1 = "peer1.org2.chainbooks.com";
//    public static final String ORG2_PEER_1_URL = "grpc://localhost:8051";

    public static final String CHAINCODE_NAME = "chainbooks";

    public static final String ADMIN = "admin";
    public static final String ROOT_DIR = System.getProperty("user.dir");
    public static final String CRYPTO_DIR = ROOT_DIR + "/chainbook-network/basic-network/crypto-config";
    public static final String ORG1_USR_ADMIN_PK = CRYPTO_DIR + "/peerOrganizations/org1.chainbooks.com/users/Admin@org1.chainbooks.com/msp/keystore";
    public static final String ORG1_USR_ADMIN_CERT = CRYPTO_DIR + "/peerOrganizations/org1.chainbooks.com/users/Admin@org1.chainbooks.com/msp/admincerts";
    public static final String ORG2_USR_ADMIN_PK = CRYPTO_DIR + "/peerOrganizations/org2.chainbooks.com/users/Admin@org2.chainbooks.com/msp/keystore";
    public static final String ORG2_USR_ADMIN_CERT = CRYPTO_DIR + "/peerOrganizations/org2.chainbooks.com/users/Admin@org2.chainbooks.com/msp/admincerts";
    public static final String CHANNEL_CONFIG_PATH = ROOT_DIR + "/chainbook-network/basic-network/config/channel.tx";
}
