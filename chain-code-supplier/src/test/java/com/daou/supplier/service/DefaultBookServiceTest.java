package com.daou.supplier.service;

import com.daou.supplier.config.SupplierConfig;
import com.daou.supplier.model.BlockchainUser;
import com.daou.supplier.model.Books;
import com.daou.supplier.model.PeerDomainConfig;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * Created by yjlee on 2018-11-13.
 */
public class DefaultBookServiceTest {

    BookService defaultBookService;

    @Before
    public void init(){

    }

    @Test
    public void givenBooks_whenAddBooks_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);

        String isbn = "112-3334-121";
        String bookName="hello blockchain";
        String writer = "tom";
        Integer amount = 12000;
        LocalDate issueDate = LocalDate.of(2018,11,21);
        Integer salesCount = 0;
        Books books = new Books(isbn,bookName,writer,amount,issueDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")),salesCount);

        PeerDomainConfig peerDomainConfig = new PeerDomainConfig(SupplierConfig.ORG1,SupplierConfig.ORG1_MSP
                ,SupplierConfig.ORG1_PEER_0,SupplierConfig.ORG1_PEER_0_URL,SupplierConfig.CA_ORG1_URL);
        defaultBookService = new DefaultBookService(admin,peerDomainConfig);
        defaultBookService.addBooks(books);

    }

    @Test
    public void givenORG1_whenGetByName_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);

        PeerDomainConfig peerDomainConfig = new PeerDomainConfig(SupplierConfig.ORG1,SupplierConfig.ORG1_MSP
                ,SupplierConfig.ORG1_PEER_0,SupplierConfig.ORG1_PEER_0_URL,SupplierConfig.CA_ORG1_URL);
        String isbn = "112-33-221";
        defaultBookService = new DefaultBookService(admin,peerDomainConfig);
        Books books = defaultBookService.getByName(isbn);

        System.out.println("books name : " + books.getName());
    }

    @Test
    public void givenORG2_whenGetByName_thenSuccess() throws Exception {

        PeerDomainConfig peerDomainConfig = new PeerDomainConfig(SupplierConfig.ORG2,SupplierConfig.ORG2_MSP
                ,SupplierConfig.ORG2_PEER_0,SupplierConfig.ORG2_PEER_0_URL,SupplierConfig.CA_ORG1_URL);

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG2);
        admin.setMspId(SupplierConfig.ORG2_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);

//        String isbn = "1111-222-333";
        defaultBookService = new DefaultBookService(admin,peerDomainConfig);
        String isbn = "112-33-22";
        Books books = defaultBookService.getByName(isbn);

        System.out.println("books name : " + books.getName());

    }

    @Test
    public void givenOrg1_whenAddBooks_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);
        String isbn = "112-33-221";
        Books books = new Books(isbn,"hello block chain","zone",1000, "20181123",0);
        PeerDomainConfig peerDomainConfig = new PeerDomainConfig(SupplierConfig.ORG1,SupplierConfig.ORG1_MSP
                ,SupplierConfig.ORG1_PEER_0,SupplierConfig.ORG1_PEER_0_URL,SupplierConfig.CA_ORG1_URL);
        defaultBookService = new DefaultBookService(admin,peerDomainConfig);
        defaultBookService.addBooks(books);

        Books searchResult = defaultBookService.getByName(isbn);

        System.out.println("searchResult name : " + searchResult.getName());
    }

    @Test
    public void givenOrg2_whenAddBooks_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG2);
        admin.setMspId(SupplierConfig.ORG2_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);
        String isbn = "112-33-221";
        Books books = new Books(isbn,"hello block chain","zone",1000, "20181123",0);
        PeerDomainConfig peerDomainConfig = new PeerDomainConfig(SupplierConfig.ORG2,SupplierConfig.ORG2_MSP
                ,SupplierConfig.ORG2_PEER_0,SupplierConfig.ORG2_PEER_0_URL,SupplierConfig.CA_ORG1_URL);
        defaultBookService = new DefaultBookService(admin,peerDomainConfig);
        defaultBookService.addBooks(books);

        Books searchResult = defaultBookService.getByName(isbn);

        System.out.println("searchResult name : " + searchResult.getName());
    }

    @Test
    public void given_whenincrementSalesCount_thenSuccess() throws Exception {

        String adminName = "admin";
        String adminPw = "adminpw";
        BlockchainUser admin = new BlockchainUser();
        admin.setAffiliation(SupplierConfig.ORG1);
        admin.setMspId(SupplierConfig.ORG1_MSP);
        admin.setName(adminName);

        UserService userService = new DefaultUserService(SupplierConfig.CA_ORG1_URL);
        admin = userService.enrollAdminUser(admin,adminName,adminPw);
        String isbn = "112-33-221";

        PeerDomainConfig peerDomainConfig = new PeerDomainConfig(SupplierConfig.ORG1,SupplierConfig.ORG1_MSP
                ,SupplierConfig.ORG1_PEER_0,SupplierConfig.ORG1_PEER_0_URL,SupplierConfig.CA_ORG1_URL);
        defaultBookService = new DefaultBookService(admin,peerDomainConfig);
        defaultBookService.incrementSalesCount(isbn,1);
    }

}