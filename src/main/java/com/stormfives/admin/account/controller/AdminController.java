package com.stormfives.admin.account.controller;

import com.stormfives.admin.account.controller.req.AdminReq;
import com.stormfives.admin.account.service.AdminService;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.common.response.FailResponse;
import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.common.response.SuccessResponse;
import com.stormfives.admin.common.util.MessageSourceUtil;
import com.stormfives.admin.token.vo.TokenVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/coin/")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @Autowired
    private AdminService adminService;



    @PostMapping("v1/login")
    public TokenVo login(@RequestBody AdminReq adminReq, HttpServletRequest request) throws InvalidArgumentException {

        return adminService.login(adminReq.getName(), adminReq.getPassword());
    }



    @PostMapping("v1/admin-wallet")
    public ResponseValue addAdmin(@RequestBody AdminReq adminReq, HttpServletRequest request) throws InvalidArgumentException {

        Integer adminId = (Integer) request.getAttribute("adminId");

        boolean success = false;
        try {
            success = adminService.addAdmin(adminReq.getName(), adminReq.getPassword(), adminReq.getPhone(), adminReq.getRealName(), adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
        }

        if (success)
            return new SuccessResponse("message", messageSourceUtil.getMessage("success"));

        return new FailResponse("fail");
    }



    @PutMapping("v1/admin-pwd")
    public ResponseValue resetPassword(@RequestBody AdminReq adminReq, HttpServletRequest request) {

        Integer adminId = (Integer) request.getAttribute("adminId");

        boolean success = false;
        try {
            success = adminService.resetPassword(adminId, adminReq.getPassword(), adminReq.getName());

        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());

        } catch (Exception e) {
        }

        if (success)
            return new SuccessResponse("message", messageSourceUtil.getMessage("success"));

        return new FailResponse("err!");
    }


}
