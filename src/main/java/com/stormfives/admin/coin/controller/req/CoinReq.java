package com.stormfives.admin.coin.controller.req;

import com.stormfives.admin.coin.dao.entity.CoinInfo;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/15
 */
public class CoinReq extends CoinInfo{


    private Integer pageNum=1;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }
}
