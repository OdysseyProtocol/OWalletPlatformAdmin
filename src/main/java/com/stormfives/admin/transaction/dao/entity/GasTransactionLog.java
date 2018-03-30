package com.stormfives.admin.transaction.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class GasTransactionLog {
    private Integer id;

    private String fromAddress;

    private String toAddress;

    private String coinName;

    private BigDecimal coinNum;

    private BigDecimal gasFee;

    private Date createdAt;

    private String orderTxHash;

    private Long nonce;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public BigDecimal getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(BigDecimal coinNum) {
        this.coinNum = coinNum;
    }

    public BigDecimal getGasFee() {
        return gasFee;
    }

    public void setGasFee(BigDecimal gasFee) {
        this.gasFee = gasFee;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrderTxHash() {
        return orderTxHash;
    }

    public void setOrderTxHash(String orderTxHash) {
        this.orderTxHash = orderTxHash;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
}