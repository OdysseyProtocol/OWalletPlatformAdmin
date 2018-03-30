package com.stormfives.admin.transaction.controller.req;

import com.stormfives.admin.user.dao.entity.UserCoinLog;

/**
 * Created by y on 2018/3/26.
 */
public class UserCoinLogReq extends UserCoinLog {

    private Integer pageNum = 1;

    private String beginTime;

    private String endTime;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }


    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
