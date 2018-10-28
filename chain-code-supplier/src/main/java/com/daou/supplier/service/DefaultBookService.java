package com.daou.supplier.service;

import com.daou.supplier.model.Books;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.sql.Time;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Created by yjlee on 2018-10-28.
 */
public class DefaultBookService implements BookService {

    private static final Logger log = Logger.getLogger(DefaultBookService.class);

    HFClient client;

    public DefaultBookService(HFClient client){
        this.client = client;
    }

    @Override
    public List<Books> getAllBooks() throws ProposalException, InvalidArgumentException {

        // get channel instance from client
        Channel channel = client.getChannel("mychannel");
        // create chaincode request
        QueryByChaincodeRequest qpr = client.newQueryProposalRequest();
        // build cc id providing the chaincode name. Version is omitted here.
        ChaincodeID fabcarCCId = ChaincodeID.newBuilder().setName("fabcar").build();
        qpr.setChaincodeID(fabcarCCId);
        // CC function to be called
        qpr.setFcn("queryAllCars");
        Collection<ProposalResponse> res = channel.queryByChaincode(qpr);
        // display response
        for (ProposalResponse pres : res) {
            String stringResponse = new String(pres.getChaincodeActionResponsePayload());
            log.info(stringResponse);
        }



        return null;
    }

    @Override
    public Books getByName(String name) {
        return null;
    }

    @Override
    public void addBooks(Books books) throws InvalidArgumentException, ProposalException, InterruptedException, ExecutionException, TimeoutException {

        Channel channel = client.getChannel("mychannel");
        TransactionProposalRequest tpr = client.newTransactionProposalRequest();
        ChaincodeID cid = ChaincodeID.newBuilder().setName("fabcar").build();
        tpr.setChaincodeID(cid);
        tpr.setFcn("createCar");
        tpr.setArgs(new String[]{"CAR11", "Skoda", "MB1000", "Yellow", "Lukas"});
        Collection<ProposalResponse> responses = channel.sendTransactionProposal(tpr);
        List<ProposalResponse> invalid = responses.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
        if (!invalid.isEmpty()) {
            invalid.forEach(response -> {
                log.error(response.getMessage());
            });
            throw new RuntimeException("invalid response(s) found");
        }
        CompletableFuture<BlockEvent.TransactionEvent> transactionEventCompletableFuture = channel.sendTransaction(responses);

        BlockEvent.TransactionEvent transactionEvent = transactionEventCompletableFuture.get(60, TimeUnit.SECONDS);

        if(transactionEvent.isValid()){
            log.info("Transacion tx: " + transactionEvent.getTransactionID() + " is completed.");
        }else{
            log.error("Transaction tx: " + transactionEvent.getTransactionID() + " is invalid.");
        }

//        return transactionEventCompletableFuture;


    }

    @Override
    public void incrementSalesCount(String name, Integer salesCount) {

    }
}
