package com.daou.supplier.service;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

/**
 * Created by yjlee on 2018-10-28.
 */
public class DefaultChannelService implements ChannelService {

    HFClient hfClient;

    public DefaultChannelService(HFClient hfClient){
        this.hfClient = hfClient;
    }

    @Override
    public Channel getChannel() throws InvalidArgumentException, TransactionException {
        // initialize channel
        // peer name and endpoint in fabcar network
        Peer peer = hfClient.newPeer("peer0.org1.example.com", "grpc://localhost:7051");
        // eventhub name and endpoint in fabcar network
        EventHub eventHub = hfClient.newEventHub("eventhub01", "grpc://localhost:7053");
        // orderer name and endpoint in fabcar network
        Orderer orderer = hfClient.newOrderer("orderer.example.com", "grpc://localhost:7050");
        // channel name in fabcar network
        Channel channel = hfClient.newChannel("mychannel");
        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();
        return channel;

    }
}
