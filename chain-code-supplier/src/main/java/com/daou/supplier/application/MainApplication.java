package com.daou.supplier.application;

import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Collection;
import java.util.Iterator;


import com.daou.supplier.model.Books;
import com.daou.supplier.model.BlockchainUser;
import com.daou.supplier.config.SupplierConfig;
import com.daou.supplier.util.Util;
import com.daou.supplier.client.FabricClient;

import org.hyperledger.fabric.sdk.ChannelConfiguration;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Peer;


/**
 * Created by yjlee on 2018-10-28.
 */
public class MainApplication {

    public static void main(String[] args){
        try {
//            Books mybook = new Books("book1234", "The Phoo", "Donghyuk Kim", 18000, "2018-12-03", 2);
//            System.out.print(mybook.getIsbn());

            BlockchainUser org1Admin = new BlockchainUser();
            File pkFolder1 = new File(SupplierConfig.ORG1_USR_ADMIN_PK);
            //        System.out.println("file dir = " + SupplierConfig.ORG1_USR_ADMIN_PK);
            File[] pkFiles1 = pkFolder1.listFiles();
            File certFolder1 = new File(SupplierConfig.ORG1_USR_ADMIN_CERT);
            File[] certFiles1 = certFolder1.listFiles();

            Enrollment enrollOrg1Admin = Util.getEnrollment(SupplierConfig.ORG1_USR_ADMIN_PK, pkFiles1[0].getName(), SupplierConfig.ORG1_USR_ADMIN_CERT, certFiles1[0].getName());
            org1Admin.setEnrollment(enrollOrg1Admin);
            org1Admin.setMspId(SupplierConfig.ORG1_MSP);
            org1Admin.setName(SupplierConfig.ADMIN);

            BlockchainUser org2Admin = new BlockchainUser();
            File pkFolder2 = new File(SupplierConfig.ORG2_USR_ADMIN_PK);
            File[] pkFiles2 = pkFolder2.listFiles();
            File certFolder2 = new File(SupplierConfig.ORG2_USR_ADMIN_CERT);
            File[] certFiles2 = certFolder2.listFiles();
            Enrollment enrollOrg2Admin = Util.getEnrollment(SupplierConfig.ORG2_USR_ADMIN_PK, pkFiles2[0].getName(),
                    SupplierConfig.ORG2_USR_ADMIN_CERT, certFiles2[0].getName());
            org2Admin.setEnrollment(enrollOrg2Admin);
            org2Admin.setMspId(SupplierConfig.ORG2_MSP);
            org2Admin.setName(SupplierConfig.ADMIN);

            System.out.print(">> org1Admind name : " + org1Admin.getName());
            System.out.print(">> org1Admind account : " + org1Admin.getAccount());
            System.out.print(">> org1Admind mspid : " + org1Admin.getMspId());


            FabricClient fabClient = new FabricClient(org1Admin);

            // Create a new channel
            Orderer orderer = fabClient.getInstance().newOrderer(SupplierConfig.ORDERER_NAME, SupplierConfig.ORDERER_URL);
            ChannelConfiguration channelConfiguration = new ChannelConfiguration(new File(SupplierConfig.CHANNEL_CONFIG_PATH));

            byte[] channelConfigurationSignatures = fabClient.getInstance()
                    .getChannelConfigurationSignature(channelConfiguration, org1Admin);

            // 채널 인스턴스 생성 (org1Admin)
            Channel mychannel = fabClient.getInstance().newChannel(SupplierConfig.CHANNEL_NAME, orderer, channelConfiguration,
                    channelConfigurationSignatures);

            // 피어들의 인스턴스 생성
            Peer peer0_org1 = fabClient.getInstance().newPeer(SupplierConfig.ORG1_PEER_0, SupplierConfig.ORG1_PEER_0_URL);
//            Peer peer1_org1 = fabClient.getInstance().newPeer(SupplierConfig.ORG1_PEER_1, SupplierConfig.ORG1_PEER_1_URL);
            Peer peer0_org2 = fabClient.getInstance().newPeer(SupplierConfig.ORG2_PEER_0, SupplierConfig.ORG2_PEER_0_URL);
//            Peer peer1_org2 = fabClient.getInstance().newPeer(SupplierConfig.ORG2_PEER_1, SupplierConfig.ORG2_PEER_1_URL);

            // 채널을 피어들에 연결하기
            mychannel.joinPeer(peer0_org1);
//            mychannel.joinPeer(peer1_org1);

            // 채널에 오더러
            mychannel.addOrderer(orderer);

            // 채널 초기화
            mychannel.initialize();

            fabClient.getInstance().setUserContext(org2Admin);
            mychannel = fabClient.getInstance().getChannel("mychannel");
            mychannel.joinPeer(peer0_org2);
//            mychannel.joinPeer(peer1_org2);

            Logger.getLogger(MainApplication.class.getName()).log(Level.INFO, "Channel created "+mychannel.getName());
            Collection peers = mychannel.getPeers();
            Iterator peerIter = peers.iterator();
            while (peerIter.hasNext())
            {
                Peer pr = (Peer) peerIter.next();
                Logger.getLogger(MainApplication.class.getName()).log(Level.INFO,pr.getName()+ " at " + pr.getUrl());
            }



        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
