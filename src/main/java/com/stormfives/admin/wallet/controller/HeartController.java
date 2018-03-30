package com.stormfives.admin.wallet.controller;

import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.common.response.SuccessResponse;
import com.stormfives.admin.log.dao.LogOperateAdminMapper;
import com.stormfives.admin.log.dao.entity.LogOperateAdmin;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("api")
public class HeartController {


    protected org.slf4j.Logger logger = LoggerFactory.getLogger(HeartController.class);
//13.250.108.218:9005/api/isAlive

    @Autowired
    LogOperateAdminMapper logOperateAdminMapper;
    @RequestMapping(value = "/isAlive", method = RequestMethod.GET)
    public @ResponseBody
    ResponseValue isAlive( ) {
        logger.warn("===================server started========");
        HashMap<String,Object> res = new HashMap<>();
        res.put("ok","ok");
       LogOperateAdmin admin =  logOperateAdminMapper.selectByPrimaryKey(1);
       admin.setParam("aaaaa");
       logOperateAdminMapper.updateByPrimaryKey(admin);
        logOperateAdminMapper.deleteByPrimaryKey(3);

        return new SuccessResponse("success",res);
    }




}
