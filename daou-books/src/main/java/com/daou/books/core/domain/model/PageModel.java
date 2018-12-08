package com.daou.books.core.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageModel<T> {

    private final PageInfo pageInfo;

    private final List<T> content;

    private Object extParameter;

    public PageModel() {
        this.content = new ArrayList<T>();
        this.pageInfo = new PageInfo(0, 0, 0);
    }

    public PageModel(Page<T> page) {
        this.content = page.getContent();
        this.pageInfo = new PageInfo(page);
    }

    public <P> PageModel(List<T> content, Page<P> page) {
        this.content = content;
        this.pageInfo = new PageInfo(page);
    }

    public PageModel(List<T> content, PageInfo pageInfo) {
        this.content = content;
        this.pageInfo = pageInfo;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public List<T> getContent() {
        return content;
    }

    public Object getExtParameter() {
        return this.extParameter;
    }

    public void setExtParameter(Object extParameter) {
        this.extParameter = extParameter;
    }
}
