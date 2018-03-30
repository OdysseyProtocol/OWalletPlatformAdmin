package com.stormfives.admin.wallet.controller.req;

import com.stormfives.admin.wallet.dao.entity.WalletGroupDetail;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/16
 * Time: 下午2:41
 */
public class WalletGroupReq {

    private Integer groupId;

    private Integer id;

    private Integer pageNum =1 ;

    private String groupName;

    private Integer merchantId; //第三方商户ID

    private Integer groupType;

    private String coinAddress; //钱包地址

    private List<WalletGroupDetail> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public List<WalletGroupDetail> getList() {
        return list;
    }

    public void setList(List<WalletGroupDetail> list) {
        this.list = list;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCoinAddress() {
        return coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }
}
