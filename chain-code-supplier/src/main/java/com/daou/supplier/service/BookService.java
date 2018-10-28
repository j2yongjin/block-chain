package com.daou.supplier.service;

import com.daou.supplier.model.Books;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yjlee on 2018-10-28.
 */
public interface BookService {

    public List<Books> getAllBooks() throws ProposalException, InvalidArgumentException;
    public Books getByName(String name);
    public void addBooks(Books books) throws InvalidArgumentException, ProposalException, InterruptedException, ExecutionException, TimeoutException;
    public void incrementSalesCount(String name , Integer salesCount);


}
