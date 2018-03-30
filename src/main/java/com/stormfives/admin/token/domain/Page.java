package com.stormfives.admin.token.domain;

import com.github.pagehelper.PageInfo;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.List;

/**
 */
public class Page implements Serializable {

    private static final long serialVersionUID = -6547334134436248960L;

    /**
     */
    private int per = 20;


    /**
     */
    private int current = 1;

    /**
     */
    private int count;

    private Object list;

    /**
     */
    private int pages;

    public Page() {
    }

    public Page(int per, int current, int count, int pages, Object object) {
        this.per = per;
        this.current = current;
        this.count = count;
        this.pages = pages;
        this.list = object;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        if (per > 50) {
            this.per = 20;
        } else {
            this.per = per;
        }
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Object getList() {
        return this.list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public static Page toPage(List<?> obj) {
        PageInfo pageInfo = new PageInfo<>(obj);
        final long total = pageInfo.getTotal();
        return new Page(pageInfo.getPageSize(), pageInfo.getPageNum(), Ints.saturatedCast(total), pageInfo.getPages(),obj);
    }

}