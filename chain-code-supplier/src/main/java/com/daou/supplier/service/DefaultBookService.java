package com.daou.supplier.service;

import com.daou.supplier.config.SupplierConfig;
import com.daou.supplier.model.BlockchainUser;
import com.daou.supplier.model.Books;
import com.daou.supplier.model.PeerDomainConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.sql.Time;
import java.time.Period;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by yjlee on 2018-10-28.
 */
public class DefaultBookService implements BookService {

    private static final String EXPECTED_EVENT_NAME = "event";
    private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);

    PeerDomainConfig peerDomainConfig;
    BlockchainUser admin;

    public DefaultBookService(BlockchainUser admin,PeerDomainConfig peerDomainConfig) {
        this.admin = admin;
        this.peerDomainConfig = peerDomainConfig;
    }

    @Override
    public List<Books> getAllBooks() throws ProposalException, InvalidArgumentException {
        return null;
    }

    @Override
    public Books getByName(String isbn) throws Exception {

        DefaultChannelService defaultChannelService = new DefaultChannelService(admin);
        ChannelCustomClient channelCustomClient = defaultChannelService.createChannelClient(SupplierConfig.CHANNEL_NAME);
        Channel channel = channelCustomClient.getChannel();

        Peer peer = defaultChannelService.getInstance().newPeer(peerDomainConfig.getPeerOrg(),peerDomainConfig.getPeerOrgUrl());
        EventHub eventHub = defaultChannelService.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
        Orderer orderer = defaultChannelService.getInstance().newOrderer(SupplierConfig.ORDERER_NAME, SupplierConfig.ORDERER_URL);

        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();

        String[] args = {isbn};

        Collection<ProposalResponse>  responsesQuery = channelCustomClient.queryByChainCode(SupplierConfig.CHAINCODE_NAME, ChainCodeFunction.FIND_BOOK.name, args);

        Books books=null;
        for (ProposalResponse pres : responsesQuery) {
            String stringResponse = new String(pres.getChaincodeActionResponsePayload());
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("response "   +  stringResponse);
            books = objectMapper.readValue(stringResponse,Books.class);
        }

        return books;
    }

//    @Override
//    public Books getByName(BlockchainUser admin, String isbn, PeerDomainConfig peerDomainConfig) throws Exception {
//
//        DefaultChannelService defaultChannelService = new DefaultChannelService(admin);
//        ChannelCustomClient channelCustomClient = defaultChannelService.createChannelClient(SupplierConfig.CHANNEL_NAME);
//        Channel channel = channelCustomClient.getChannel();
//
//        Peer peer = defaultChannelService.getInstance().newPeer(peerDomainConfig.getPeerOrg(),peerDomainConfig.getPeerOrgUrl());
//        EventHub eventHub = defaultChannelService.getInstance().newEventHub("eventhub01", "grpc://localhost:7053");
//        Orderer orderer = defaultChannelService.getInstance().newOrderer(SupplierConfig.ORDERER_NAME, SupplierConfig.ORDERER_URL);
//
//        channel.addPeer(peer);
//        channel.addEventHub(eventHub);
//        channel.addOrderer(orderer);
//        channel.initialize();
//
//        String[] args = {isbn};
//
//        Collection<ProposalResponse>  responsesQuery = channelCustomClient.queryByChainCode(SupplierConfig.CHAINCODE_NAME, ChainCodeFunction.FIND_BOOK.name, args);
//
//        Books books=null;
//        for (ProposalResponse pres : responsesQuery) {
//            String stringResponse = new String(pres.getChaincodeActionResponsePayload());
//            ObjectMapper objectMapper = new ObjectMapper();
//            System.out.println("response "   +  stringResponse);
//            books = objectMapper.readValue(stringResponse,Books.class);
//        }
//
//        return books;
//    }

    @Override
    public void addBooks(Books books) throws Exception {

        ChannelService channelService = new DefaultChannelService(admin);
        ChannelCustomClient channelCustomClient = channelService.createChannelClient(SupplierConfig.CHANNEL_NAME);
        Channel channel = channelCustomClient.getChannel();

        Peer peer = channelService.getInstance().newPeer(peerDomainConfig.getPeerOrg(), peerDomainConfig.getPeerOrgUrl());

        EventHub eventHub = channelService.getInstance().newEventHub("eventhub01", "grpc://127.0.0.1:7053");
        Orderer orderer = channelService.getInstance().newOrderer(SupplierConfig.ORDERER_NAME, SupplierConfig.ORDERER_URL);

        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();

        TransactionProposalRequest request = channelService.getInstance().newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName(SupplierConfig.CHAINCODE_NAME).build();
        request.setChaincodeID(ccid);

        request.setFcn(ChainCodeFunction.ADD_BOOK.getName());
        request.setArgs(books.getArgs());
        request.setProposalWaitTime(1000);

        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
        request.setTransientMap(tm2);
        Collection<ProposalResponse> responses = channelCustomClient.sendTransactionProposal(request);
        for (ProposalResponse res: responses) {
            ChaincodeResponse.Status status = res.getStatus();

            java.util.logging.Logger.getLogger(DefaultBookService.class.getName()).log(Level.INFO,"Invoked books on "+SupplierConfig.CHAINCODE_NAME + ". Status - " + status);
        }

    }

    @Override
    public void incrementSalesCount(String isbn, Integer salesCount) throws Exception {

        ChannelService channelService = new DefaultChannelService(admin);
        ChannelCustomClient channelCustomClient = channelService.createChannelClient(SupplierConfig.CHANNEL_NAME);
        Channel channel = channelCustomClient.getChannel();

        Peer peer = channelService.getInstance().newPeer(peerDomainConfig.getPeerOrg(), peerDomainConfig.getPeerOrgUrl());

        EventHub eventHub = channelService.getInstance().newEventHub("eventhub01", "grpc://127.0.0.1:7053");
        Orderer orderer = channelService.getInstance().newOrderer(SupplierConfig.ORDERER_NAME, SupplierConfig.ORDERER_URL);

        channel.addPeer(peer);
        channel.addEventHub(eventHub);
        channel.addOrderer(orderer);
        channel.initialize();

        TransactionProposalRequest request = channelService.getInstance().newTransactionProposalRequest();
        ChaincodeID ccid = ChaincodeID.newBuilder().setName(SupplierConfig.CHAINCODE_NAME).build();
        request.setChaincodeID(ccid);

        request.setFcn(ChainCodeFunction.INCREMENT_SALES_BOOK.getName());
        String salesCount1 = String.valueOf(salesCount);
        ArrayList args = new ArrayList();
        args.add(isbn);
        args.add(String.valueOf(salesCount));
        request.setArgs(args);
        request.setProposalWaitTime(1000);

        Map<String, byte[]> tm2 = new HashMap<>();
        tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8));
        tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8));
        tm2.put("result", ":)".getBytes(UTF_8));
        tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA);
        request.setTransientMap(tm2);
        Collection<ProposalResponse> responses = channelCustomClient.sendTransactionProposal(request);
        for (ProposalResponse res: responses) {
            ChaincodeResponse.Status status = res.getStatus();
            java.util.logging.Logger.getLogger(DefaultBookService.class.getName()).log(Level.INFO,"Invoked books on "+SupplierConfig.CHAINCODE_NAME + ". Status - " + status);
        }

    }
}
