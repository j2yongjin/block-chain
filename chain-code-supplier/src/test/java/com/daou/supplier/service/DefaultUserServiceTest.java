package com.daou.supplier.service;

import com.daou.supplier.config.SupplierConfig;
import com.daou.supplier.model.BlockchainUser;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by yjlee on 2018-11-18.
 */
public class DefaultUserServiceTest {

    UserService userService;
    BlockchainUser adminUser;
    @Before
    public void init() throws Exception {
        userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
    }

    @Test
    public void whenEnrollAdminUser_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        adminUser = userService.enrollAdminUser(admin,adminName,adminPw);
        assertNotNull(adminUser);
    }

    @Test
    public void whenEnrollUser_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        adminUser = userService.enrollAdminUser(admin,adminName,adminPw);

        BlockchainUser user = new BlockchainUser();
        String userName = "userName1";
        user.setName(userName);
        user.setAffiliation(SupplierConfig.ORG1);
        user.setMspId(SupplierConfig.ORG1_MSP);

        userService.enrollUser(user,adminUser);

    }





}