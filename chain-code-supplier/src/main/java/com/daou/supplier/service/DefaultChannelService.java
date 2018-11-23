package com.daou.supplier.service;

import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

/**
 * Created by yjlee on 2018-10-28.
 */
public class DefaultChannelService implements ChannelService {

    HFClient hfClient;


    public DefaultChannelService(User user) throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client
        hfClient = HFClient.createNewInstance();
        hfClient.setCryptoSuite(cryptoSuite);
        hfClient.setUserContext(user);
    }

    public ChannelCustomClient createChannelClient(String name) throws InvalidArgumentException {
        Channel channel = hfClient.newChannel(name);
        ChannelCustomClient client = new ChannelCustomClient(name, channel, this);
        return client;
    }

    public HFClient getInstance() {
        return hfClient;
    }

}
