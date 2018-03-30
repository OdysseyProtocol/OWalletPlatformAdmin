package com.stormfives.admin.user.controller.req;

import com.stormfives.admin.user.dao.entity.UserWalletInfo;

/**
 * Created by y on 2018/3/27.
 */
public class UserWalletInfoReq extends UserWalletInfo {
    private Integer pageNum = 1;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
