package com.daou.books.core.domain.model;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public class PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int page;
    private int offset;
    private long total;
    private boolean isLastPage;

    private Sort sort;

    public <F> PageInfo(Page<F> page) {
        super();
        this.page = page.getNumber();
        this.offset = page.getSize();
        this.total = page.getTotalElements();
        this.isLastPage = page.isLast();
        this.sort = page.getSort();
    }

    public PageInfo(int page, int offset, long total) {
        super();
        this.page = page;
        this.offset = offset;
        this.total = total;
        this.isLastPage = ((page + 1) * offset) >= total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
