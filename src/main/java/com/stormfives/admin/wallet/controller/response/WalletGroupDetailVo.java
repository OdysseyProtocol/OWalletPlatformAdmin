package com.stormfives.admin.wallet.controller.response;

import com.stormfives.admin.wallet.dao.entity.WalletGroupCoinBalance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/19
 * Time: 下午3:15
 */
public class WalletGroupDetailVo {
    private Integer id;

    private Integer groupId;

    private String walletAddress;

    private Double balance;

    private Date updatedAt;

    private Integer updatedBy;

    private Date createdAt;

    private Integer createdBy;

    private Long nonce;


    private List<WalletGroupCoinBalance> balanceList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public List<WalletGroupCoinBalance> getBalanceList() {
        return balanceList;
    }

    public void setBalanceList(List<WalletGroupCoinBalance> balanceList) {
        this.balanceList = balanceList;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
}
