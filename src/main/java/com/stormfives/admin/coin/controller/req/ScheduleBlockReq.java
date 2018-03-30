package com.stormfives.admin.coin.controller.req;

import com.stormfives.admin.coin.dao.entity.ScheduleBlockNum;

/**
 * Created by y on 2018/3/20.
 */
public class ScheduleBlockReq extends ScheduleBlockNum {
    Integer currentPage;


    Integer scheduleBlockId;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getScheduleBlockId() {
        return scheduleBlockId;
    }

    public void setScheduleBlockId(Integer scheduleBlockId) {
        this.scheduleBlockId = scheduleBlockId;
    }
}
