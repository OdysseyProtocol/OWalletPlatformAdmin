package com.stormfives.admin.coin.controller;

import com.alibaba.fastjson.JSON;
import com.stormfives.admin.coin.controller.req.ScheduleBlockReq;
import com.stormfives.admin.coin.service.ScheduleBlockService;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.common.response.FailResponse;
import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.common.response.SuccessResponse;
import com.stormfives.admin.common.util.MessageSourceUtil;
import com.stormfives.admin.token.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by y on 2018/3/20.
 */
@RestController
public class ScheduleBlockController {

    Logger logger = LoggerFactory.getLogger(ScheduleBlockController.class);

    @Autowired
    private ScheduleBlockService scheduleBlockService;


    @Autowired
    MessageSourceUtil messageSourceUtil;

    @GetMapping("/schedule-block")
    public Page getScheduleBlock(@ModelAttribute ScheduleBlockReq scheduleBlockReq) {
        return scheduleBlockService.getScheduleBlock(scheduleBlockReq);
    }


    @PutMapping("/schedule-block")
    public ResponseValue editScheduleBlock(@RequestBody ScheduleBlockReq scheduleBlockReq, HttpServletRequest request) {
        boolean status = false;
        try {
            status = scheduleBlockService.editScheduleBlock(scheduleBlockReq, request);
        } catch (InvalidArgumentException e) {
            logger.error(" param:{}", JSON.toJSON(scheduleBlockReq), e);
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            logger.error(" param:{}", JSON.toJSON(scheduleBlockReq), e);
            return new FailResponse(e.getMessage());
        }
        return status ? new SuccessResponse("message", messageSourceUtil.getMessage("success")) : new FailResponse("false");
    }

    @DeleteMapping("/schedule-block")
    public ResponseValue deleteScheduleBlock(@RequestBody ScheduleBlockReq scheduleBlockReq, HttpServletRequest request) {
        boolean success = false;
        try {
            Integer coinId = scheduleBlockReq.getScheduleBlockId();
            if (coinId == null) {
                logger.error(" param:{})", JSON.toJSON(scheduleBlockReq));
                return new FailResponse("coinId  is null");
            }
            success = scheduleBlockService.deleteScheduleBlock(coinId, request);
        } catch (InvalidArgumentException e) {
            logger.error(" param:{})", JSON.toJSON(scheduleBlockReq), e);
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            logger.error(" param:{})", JSON.toJSON(scheduleBlockReq), e);
            return new FailResponse(e.getMessage());
        }
        return success ? new SuccessResponse("message", messageSourceUtil.getMessage("success")) : new FailResponse("false");
    }


    @PostMapping("/schedule-block")
    public ResponseValue addScheduleBlock(@RequestBody ScheduleBlockReq scheduleBlockReq, HttpServletRequest request) {
        boolean success = false;
        try {
            success = scheduleBlockService.addScheduleBlock(scheduleBlockReq, request);
        } catch (InvalidArgumentException e) {
            logger.error(" param{}", JSON.toJSON(scheduleBlockReq), e);
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            logger.error(" param{}", JSON.toJSON(scheduleBlockReq), e);
            return new FailResponse(e.getMessage());
        }
        return success ? new SuccessResponse("message", messageSourceUtil.getMessage("success")) : new FailResponse("false");

    }


}
