package com.daou.supplier.helper;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Created by yjlee on 2018-10-28.
 */
public class NetworkHelper {

    public static HFClient getClient() throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return  client;
    }

    public static HFCAClient getCaClient(String url , Properties caClientProperties) throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFCAClient hfcaClient = HFCAClient.createNewInstance(url,caClientProperties);
        hfcaClient.setCryptoSuite(cryptoSuite);
        return hfcaClient;
    }
}
