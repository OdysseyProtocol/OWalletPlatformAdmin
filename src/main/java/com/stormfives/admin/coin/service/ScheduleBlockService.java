package com.stormfives.admin.coin.service;

import com.github.pagehelper.PageHelper;
import com.stormfives.admin.coin.controller.req.ScheduleBlockReq;
import com.stormfives.admin.coin.dao.ScheduleBlockNumMapper;
import com.stormfives.admin.coin.dao.entity.ScheduleBlockNum;
import com.stormfives.admin.coin.dao.entity.ScheduleBlockNumExample;
import com.stormfives.admin.common.constant.Constants;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.token.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by y on 2018/3/21.
 */
@Service
public class ScheduleBlockService {



    @Autowired
    ScheduleBlockNumMapper scheduleBlockNumMapper;


    /**
     *
     * @param scheduleBlockReq
     * @return
     */
    public Page getScheduleBlock(ScheduleBlockReq scheduleBlockReq) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(scheduleBlockReq.getCurrentPage(), Constants.PAGE_SIZE);
        List<ScheduleBlockNum> scheduleBlockNa = scheduleBlockNumMapper.selectByExample(new ScheduleBlockNumExample());
        return Page.toPage(scheduleBlockNa);

    }


    public boolean editScheduleBlock(ScheduleBlockReq scheduleBlockReq, HttpServletRequest request) throws InvalidArgumentException {

        if (scheduleBlockReq == null) {
            throw new InvalidArgumentException(" ");
        }

        if (scheduleBlockReq.getCoinId() == null || scheduleBlockReq.getCoinId() <= 0) {
            throw new InvalidArgumentException(" ");
        }
        if (scheduleBlockReq.getStartBlockNum() == null) {
            throw new InvalidArgumentException("start BlockNumber is null");

        }
        if (scheduleBlockReq.getEndBlockNum() == null) {
            throw new InvalidArgumentException("End BlockNumber is null");
        }

        int i = scheduleBlockNumMapper.updateByPrimaryKeySelective(scheduleBlockReq);
        return true;
    }

    public boolean deleteScheduleBlock(Integer id, HttpServletRequest request) throws InvalidArgumentException {
        if (id == null) {
            throw new InvalidArgumentException("id is null");
        }
        int i = scheduleBlockNumMapper.deleteByPrimaryKey(id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addScheduleBlock(ScheduleBlockReq scheduleBlockReq, HttpServletRequest request) throws InvalidArgumentException {

        if (scheduleBlockReq.getCoinId() == null || scheduleBlockReq.getCoinId() <= 0) {
            throw new InvalidArgumentException("id is err");
        }
        if (scheduleBlockReq.getStartBlockNum() == null) {
            throw new InvalidArgumentException("start BlockNumber is null");

        }
        if (scheduleBlockReq.getEndBlockNum() == null) {
            throw new InvalidArgumentException("End BlockNumber is null");
        }
        ScheduleBlockNum scheduleBlockNum = new ScheduleBlockNum();
        scheduleBlockNum.setCoinId(scheduleBlockReq.getCoinId());
        scheduleBlockNum.setStartBlockNum(scheduleBlockReq.getStartBlockNum());
        scheduleBlockNum.setEndBlockNum(scheduleBlockReq.getEndBlockNum());
        scheduleBlockNumMapper.insertSelective(scheduleBlockNum);
        return true;
    }
}
