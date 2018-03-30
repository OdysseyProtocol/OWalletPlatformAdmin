package com.stormfives.admin.coin.controller;

import com.alibaba.fastjson.JSON;
import com.stormfives.admin.coin.controller.req.CoinReq;
import com.stormfives.admin.coin.dao.entity.RechargeGatherRule;
import com.stormfives.admin.coin.service.CoinService;
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
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/14
 */
@RestController
@RequestMapping("/api/coin/")
public class CoinController {


    @Autowired
    private CoinService coinService;

    private Logger logger = LoggerFactory.getLogger(CoinController.class);

    @Autowired
    private MessageSourceUtil messageSourceUtil;


    @GetMapping("v1/coin-info")
    public Page getCoinList(@ModelAttribute CoinReq coinReq, HttpServletRequest request) throws InvalidArgumentException {


        return coinService.getCoinList(coinReq);
    }


    @PostMapping("v1/coin-info")
    public ResponseValue addCoinInfo(@RequestBody CoinReq coinReq, HttpServletRequest request) throws InvalidArgumentException {


        Integer adminId = (Integer)request.getAttribute("adminId");

        boolean success = false;
        try {
            success = coinService.addCoinInfo(coinReq.getCoinName(),coinReq.getContractAddress(),coinReq.getFullName(),adminId);
        } catch (InvalidArgumentException e) {
            logger.error(" param: {}", JSON.toJSONString(coinReq),e);
        }catch (Exception e) {
            logger.error("param: {}", JSON.toJSONString(coinReq),e);

        }


        return success ? new SuccessResponse("message",messageSourceUtil.getMessage("success")): new FailResponse("false");
    }


    @PutMapping("v1/coin-info")
    public ResponseValue editCoinInfo(@RequestBody CoinReq coinReq, HttpServletRequest request) throws InvalidArgumentException {


        Integer adminId = (Integer)request.getAttribute("adminId");

        boolean success = false;
        try {
            success = coinService.editCoinInfo(coinReq.getId(),coinReq.getCoinName(),coinReq.getContractAddress(),coinReq.getFullName(),
                    coinReq.getCoinHigherLimit(),coinReq.getCoinLowerLimit(),coinReq.getDayCycleRound(),adminId);
        } catch (InvalidArgumentException e) {
            logger.error("param: {}", JSON.toJSONString(coinReq),e);
            return new FailResponse(e.getMessage());
        }catch (Exception e) {
            logger.error("param: {}", JSON.toJSONString(coinReq),e);
            return new FailResponse("false");

        }


        return success ? new SuccessResponse("message",messageSourceUtil.getMessage("success")): new FailResponse("false");
    }

    @GetMapping("v1/recharge-gather-rule")
    public RechargeGatherRule getRechargeGatherRule(@RequestParam("coinId") Integer id){

        return coinService.getRechargeGatherRuleByCoidId(id);
    }

    @PutMapping("v1/recharge-gather-rule")
    public ResponseValue editRechargeGatherRule(@RequestBody RechargeGatherRule rechargeGatherRule, HttpServletRequest request) throws InvalidArgumentException {


        Integer adminId = (Integer)request.getAttribute("adminId");

        boolean success = false;
        try {
            success = coinService.editRechargeGatherRule(rechargeGatherRule.getCoinId(),rechargeGatherRule.getCoinHigherLimit(),
                    rechargeGatherRule.getCoinLowerLimit(),rechargeGatherRule.getDayCycleRound(),adminId);
        } catch (InvalidArgumentException e) {
            logger.error("param: {}", JSON.toJSONString(rechargeGatherRule),e);
            return new FailResponse(e.getMessage());
        }catch (Exception e) {
            logger.error("param: {}", JSON.toJSONString(rechargeGatherRule),e);
            return new FailResponse("false");

        }


        return success ? new SuccessResponse("message",messageSourceUtil.getMessage("success")): new FailResponse("false");
    }


    @DeleteMapping("v1/coin-info")
    public ResponseValue deleteCoinInfo(@RequestBody CoinReq coinReq, HttpServletRequest request) throws InvalidArgumentException {


        Integer adminId = (Integer)request.getAttribute("adminId");

        boolean success = false;
        try {
            success = coinService.deleteCoinInfo(coinReq.getId(),adminId);
        } catch (InvalidArgumentException e) {
            logger.error("param: {}", JSON.toJSONString(coinReq),e);
            return new FailResponse(e.getMessage());
        }catch (Exception e) {
            logger.error("param: {}", JSON.toJSONString(coinReq),e);
            return new FailResponse("false");

        }
        return null;
    }

}
