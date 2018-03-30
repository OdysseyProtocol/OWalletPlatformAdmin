package com.stormfives.admin.wallet.controller.response;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/22
 * Time: 下午1:44
 */
public class MerchantWalletGroupVo {

    private Integer merchantId;

    private Integer groupId;

    private String merchantName;

    private String groupName;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
