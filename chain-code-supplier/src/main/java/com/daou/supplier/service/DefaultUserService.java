package com.daou.supplier.service;

import com.daou.supplier.model.BlockchainUser;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

/**
 * Created by yjlee on 2018-11-18.
 */
public class DefaultUserService implements UserService {

    String caUrl;
    HFCAClient instance;

    BlockchainUser blockchainUser;

    public DefaultUserService(String caUrl) throws Exception {
        this.caUrl = caUrl;
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        instance = HFCAClient.createNewInstance(caUrl, null);
        instance.setCryptoSuite(cryptoSuite);
    }

    public HFCAClient getInstance() {
        return instance;
    }

    @Override
    public BlockchainUser enrollAdminUser(BlockchainUser admin ,String id,String password) throws Exception {
        Enrollment adminEnrollment = instance.enroll(id, password);
        admin.setEnrollment(adminEnrollment);
        return admin;
    }

    @Override
    public BlockchainUser enrollUser(BlockchainUser user,BlockchainUser admin) throws Exception {

        RegistrationRequest registrationRequest = new RegistrationRequest(user.getName(), user.getAffiliation());
        String enrollmentSecret = instance.register(registrationRequest, admin);

        Enrollment enrollment = instance.enroll(user.getName(), enrollmentSecret);
        user.setEnrollment(enrollment);
        return user;
    }
}
