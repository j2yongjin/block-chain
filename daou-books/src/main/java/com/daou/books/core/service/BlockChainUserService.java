package com.daou.books.core.service;

import com.daou.books.core.domain.model.BlockchainUserModel;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

public interface BlockChainUserService {

    BlockchainUserModel enrollAdminUser(BlockchainUserModel admin, String id, String password) throws Exception;

    BlockchainUserModel enrollUser(BlockchainUserModel user,BlockchainUserModel admin) throws Exception;

    HFCAClient getInstance();

}
