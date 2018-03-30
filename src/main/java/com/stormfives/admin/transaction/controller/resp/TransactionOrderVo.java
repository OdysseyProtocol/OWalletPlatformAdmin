package com.stormfives.admin.transaction.controller.resp;

import com.stormfives.admin.transaction.dao.entity.TransactionOrder;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/22
 */
public class TransactionOrderVo extends TransactionOrder {

    private String merchantName;

    private Integer userid;

    private Integer merchantId;

    public String getMerchantName() {

        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMerchantId() {
        return merchantId;
    }
}
