package com.stormfives.admin.user.controller.reqp;

import com.stormfives.admin.user.dao.entity.UserCoinBalance;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/21
 * Time: 下午6:00
 */
public class UserInfoVo extends UserCoinBalance {

    private String coinAddress;

    private String merchantName;

    private Date createdAt;

    public String getCoinAddress() {
        return coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
