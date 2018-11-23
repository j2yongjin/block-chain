package com.daou.supplier.service;

import com.daou.supplier.model.BlockchainUser;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;

/**
 * Created by yjlee on 2018-10-28.
 */
public interface UserService {

    BlockchainUser enrollAdminUser(BlockchainUser admin ,String id,String password) throws Exception;
    BlockchainUser enrollUser(BlockchainUser user,BlockchainUser admin) throws Exception;
    HFCAClient getInstance();

}
