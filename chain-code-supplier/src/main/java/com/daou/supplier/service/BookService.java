package com.daou.supplier.service;

import com.daou.supplier.model.BlockchainUser;
import com.daou.supplier.model.Books;
import com.daou.supplier.model.PeerDomainConfig;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yjlee on 2018-10-28.
 */
public interface BookService {

    List<Books> getAllBooks() throws Exception;
    void addBooks(Books books) throws Exception;
    Books getByName(String isbn) throws Exception ;
    void incrementSalesCount(String isbn, Integer salesCount) throws Exception;


}
