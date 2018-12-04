package com.daou.books.service;

import com.daou.books.domain.DaouBookTest;
import com.daou.books.repository.DaouBookServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("daouBookService")
public class DaouBookServiceImpl implements DaouBookService {

    @Autowired
    private DaouBookServiceRepository daouBookServiceRepository;

    @Override
    public List<DaouBookTest> getBookList() {
        List<DaouBookTest> daouBookTests =  daouBookServiceRepository.findAll();
        for (DaouBookTest daouBookTest: daouBookTests) {
            log.info(String.format("id: %d, name: %s", daouBookTest.getId(), daouBookTest.getName()));
        }
        return daouBookTests;
    }
}
