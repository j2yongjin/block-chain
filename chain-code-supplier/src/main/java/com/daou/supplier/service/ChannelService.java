package com.daou.supplier.service;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

/**
 * Created by yjlee on 2018-10-28.
 */
public interface ChannelService {

    ChannelCustomClient createChannelClient(String name) throws InvalidArgumentException;
    HFClient getInstance();
}
