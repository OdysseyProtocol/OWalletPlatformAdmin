package com.stormfives.admin.wallet.controller.req;

import com.stormfives.admin.wallet.dao.entity.MerchantInfo;
import com.stormfives.admin.wallet.dao.entity.WalletGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/17
 * Time: 下午12:12
 */
public class MerchantInfoReq extends MerchantInfo {

    private Integer pageNum=1;

    private List<WalletGroup> list = new ArrayList<>();

    private Integer groupId;



    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<WalletGroup> getList() {
        return list;
    }

    public void setList(List<WalletGroup> list) {
        this.list = list;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
