package com.daou.supplier.service;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

/**
 * Created by yjlee on 2018-10-28.
 */
public interface ChannelService {

    public Channel getChannel() throws InvalidArgumentException, TransactionException;
}
