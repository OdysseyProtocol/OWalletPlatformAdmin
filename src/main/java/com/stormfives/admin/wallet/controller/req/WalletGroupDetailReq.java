package com.stormfives.admin.wallet.controller.req;

import com.stormfives.admin.wallet.dao.entity.WalletGroupDetail;

/**
 * Created by y on 2018/3/28.
 */
public class WalletGroupDetailReq extends WalletGroupDetail {

    private Integer groupType;


    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }



}
