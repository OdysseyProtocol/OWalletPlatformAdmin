package com.stormfives.admin.user.controller.reqp;


import com.stormfives.admin.user.dao.entity.UserCoinBalance;

import java.util.List;

/**
 * Created by y on 2018/3/14.
 */
public class CoinBalanceReqp {

    Integer currentPage;
    Integer pageSize;
    List<UserCoinBalance> dataList;

    private Long total;

    public void setDataList(List<UserCoinBalance> list) {
        dataList = list;
    }


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<UserCoinBalance> getDataList() {
        return dataList;
    }

    public void setTotal(long total) {
        this.total = total;
    }


}
