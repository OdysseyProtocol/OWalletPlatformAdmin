package com.stormfives.admin.transaction.controller.req;

import jnr.ffi.annotations.In;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/21
 * Time: 下午5:47
 */
public class TransactionOrderReq {

    private Integer pageNum =1;

    private Date beginTradingTime;

    private Date endTradingTime;

    private Integer merchantId;

    private Integer userid;

    private String coinAddress;

    private Integer orderStatus;
    private String txHash;

    private Integer transactionType;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Date getBeginTradingTime() {
        return beginTradingTime;
    }

    public void setBeginTradingTime(Date beginTradingTime) {
        this.beginTradingTime = beginTradingTime;
    }

    public Date getEndTradingTime() {
        return endTradingTime;
    }

    public void setEndTradingTime(Date endTradingTime) {
        this.endTradingTime = endTradingTime;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getCoinAddress() {
        return coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }


    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
