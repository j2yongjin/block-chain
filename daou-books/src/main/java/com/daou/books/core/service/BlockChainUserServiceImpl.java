package com.daou.books.core.service;

import com.daou.books.core.domain.model.BlockchainUserModel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

public class BlockChainUserServiceImpl implements BlockChainUserService {

    String caUrl;
    HFCAClient instance;

    BlockchainUserModel blockchainUserModel;

    public BlockChainUserServiceImpl(String caUrl) throws Exception {
        this.caUrl = caUrl;
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        instance = HFCAClient.createNewInstance(caUrl, null);
        instance.setCryptoSuite(cryptoSuite);
    }

    @Override
    public HFCAClient getInstance() {
        return instance;
    }

    @Override
    public BlockchainUserModel enrollAdminUser(BlockchainUserModel admin , String id, String password) throws Exception {
        Enrollment adminEnrollment = instance.enroll(id, password);
        admin.setEnrollment(adminEnrollment);
        return admin;
    }

    @Override
    public BlockchainUserModel enrollUser(BlockchainUserModel user, BlockchainUserModel admin) throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest(user.getName(), user.getAffiliation());
        String enrollmentSecret = instance.register(registrationRequest, admin);

        Enrollment enrollment = instance.enroll(user.getName(), enrollmentSecret);
        user.setEnrollment(enrollment);
        return user;
    }
}
