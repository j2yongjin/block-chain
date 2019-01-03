package com.daou.fabric.chaincode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ByteString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperledger.fabric.shim.Chaincode;
import org.hyperledger.fabric.shim.ChaincodeBase;
import org.hyperledger.fabric.shim.ChaincodeStub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import io.netty.handler.ssl.OpenSsl;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import static com.daou.fabric.chaincode.ChainCodeFunction.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created by yjlee on 2018-10-28.
 */
public class DefaultChaincode extends ChaincodeBase{

    private static Log logger = LogFactory.getLog(DefaultChaincode.class);

    @Override
    public Response init(ChaincodeStub stub) {

        try {
            logger.info("Init java simple chaincode");
            String func = stub.getFunction();
            if (!func.equals("init")) {
                return newErrorResponse("function other than init is not supported");
            }
            List<String> args = stub.getParameters();
            if (args.size() != 4) {
                newErrorResponse("Incorrect number of arguments. Expecting 4");
            }
            // Initialize the chaincode
            Books books = new Books("1111-222-333","hello blockchain","lee",1000,"20181123",0);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValue = objectMapper.writeValueAsString(books);

            stub.putStringState(books.getIsbn(),jsonValue);

            return newSuccessResponse();
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    @Override
    public Response invoke(ChaincodeStub stub) {
        try {
            logger.info("Invoke java simple chaincode");
            String func = stub.getFunction();
            List<String> params = stub.getParameters();
            switch (ChainCodeFunction.getChainCodeFunction(func)){
                case ADD_BOOK:
                    return newBooks(stub,params);
                case DELETE_BOOK:
                    delete(stub,params);
                case FIND_BOOK:
                    return findOneByKey(stub,params);
                case FIND_ALL:
                    return findAllBooks(stub);
                case INCREMENT_SALES_BOOK:
                    return updateSaleCountByKey(stub,params);
                default:
                    return newErrorResponse("Invalid invoke function name. Expecting one of: [\"addbook\", \"delete_book\", \"find_book\", \"increment_sales_book\"]");
            }
        } catch (Throwable e) {
            return newErrorResponse(e);
        }
    }

    private Response updateSaleCountByKey(ChaincodeStub stub, List<String> params) throws IOException {

        String isbn = params.get(0);
        Integer updateCount = Integer.valueOf(params.get(1));

        String books = stub.getStringState(isbn);
        Books booksResult = Books.getNewInstanceWithJsonString(books);
        booksResult.setSalesCount(booksResult.getSalesCount() + updateCount);
        ObjectMapper objectMapper = new ObjectMapper();
        String booksJsonResult = objectMapper.writeValueAsString(booksResult);
        stub.putStringState(isbn,booksJsonResult);

        logger.info(String.format(">>> Updated Sales Count:\n-ISBN : %s\n-IssueDate : %s\n-Amount : %d\n-SalesCount : %d", booksResult.getIsbn(), booksResult.getIssueDate(), booksResult.getAmount(), booksResult.getSalesCount()));

        return newSuccessResponse("invoke finished successfully", ByteString.copyFrom(isbn + ": " + isbn + " " + updateCount + ": " + updateCount, UTF_8).toByteArray());
    }

    private Response newBooks(ChaincodeStub stub, List<String> args) throws JsonProcessingException {
        Books books = Books.getNewInstance(args);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(books);
        stub.putStringState(books.getIsbn(),jsonString);

        logger.info(String.format(">>> Added New Book:\n-ISBN: %s\n-IssueDate : %s\n-Amount : %d\n-SalesCount : %d", books.getIsbn(), books.getIssueDate(), books.getAmount(), books.getSalesCount()));

        return newSuccessResponse("invoke finished successfully", ByteString.copyFrom("isbn" + ": " + books.getIsbn() + " " + "saleCount " + ": " + books.getSalesCount(), UTF_8).toByteArray());
    }

    // Deletes an entity from state
    private Response delete(ChaincodeStub stub, List<String> args) {
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting 1");
        }
        String key = args.get(0);
        // Delete the key from the state in ledger
        stub.delState(key);
        return newSuccessResponse();
    }

    private Response findAllBooks(ChaincodeStub stub){
        QueryResultsIterator<KeyValue> rangeResult = stub.getStateByRange("0", "9999-9999-9999");
        StringBuilder allData = new StringBuilder();
        for (KeyValue keyval: rangeResult) {
            String key = keyval.getKey();
            String data = stub.getStringState(key);
            allData.append(data);
            allData.append("\n");
        }
        allData.deleteCharAt(allData.length()-1);

        String allDataStr = allData.toString();
        return newSuccessResponse(allDataStr, ByteString.copyFrom(allDataStr, UTF_8).toByteArray());

    }

    private Response findOneByKey(ChaincodeStub stub,List<String> args){
        if (args.size() != 1) {
            return newErrorResponse("Incorrect number of arguments. Expecting name of the person to query");
        }
        String key = args.get(0);

        String data = stub.getStringState(key);

        logger.info(String.format("Query Response:\nName: %s, data: %s\n", key, data));
        return newSuccessResponse(data, ByteString.copyFrom(data, UTF_8).toByteArray());

    }

    public static void main(String[] args) {
        System.out.println("OpenSSL avaliable: " + OpenSsl.isAvailable());
        new DefaultChaincode().start(args);
    }

}
