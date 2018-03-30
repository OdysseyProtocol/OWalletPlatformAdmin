package com.stormfives.admin.transaction.controller.req;

import com.stormfives.admin.transaction.dao.entity.TransactionOrder;

/**
 * Created by y on 2018/3/26.
 */
public class TransactionOrderListReq extends TransactionOrder {


    private String beginTradingTime;

    private String endTradingTime;

    Integer pageNum  = 1;



    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }


    public String getBeginTradingTime() {
        return beginTradingTime;
    }

    public void setBeginTradingTime(String beginTradingTime) {
        this.beginTradingTime = beginTradingTime;
    }

    public String getEndTradingTime() {
        return endTradingTime;
    }

    public void setEndTradingTime(String endTradingTime) {
        this.endTradingTime = endTradingTime;
    }
}
