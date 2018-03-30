package com.stormfives.admin.transaction.controller;

import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.common.response.FailResponse;
import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.common.response.SuccessResponse;
import com.stormfives.admin.common.util.MessageSourceUtil;
import com.stormfives.admin.token.domain.Page;
import com.stormfives.admin.transaction.controller.req.*;
import com.stormfives.admin.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/21
 */
@RequestMapping("/api/coin/")
@RestController
public class TransactionController {


    private Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("v1/recharge-list")
    public Page getRechargeList(@ModelAttribute TransactionOrderReq transactionOrderReq) {


        return transactionService.getRechargeList(transactionOrderReq);
    }

    @GetMapping("v1/transaction-list")
    public Page getTransactionList(@ModelAttribute TransactionOrderReq transactionOrderReq) {


        return transactionService.getTransactionList(transactionOrderReq);
    }

    /**
     * 确认到账
     */
    @PutMapping("v1/confirm-order")
    public ResponseValue confirmOrder(HttpServletRequest request, @RequestBody TransactionOrderReq transactionOrderReq) {

        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            transactionService.confirmOrder(transactionOrderReq.getTxHash(), adminId);
        } catch (InvalidArgumentException e) {
            logger.error("确认订单逻辑异常", e);
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            logger.error("确认订单程序异常", e);
            return new FailResponse(e.getMessage());
        }

        return new SuccessResponse(messageSourceUtil.getMessage("success"));
    }


    @GetMapping("v1/defeat-order-log")
    public Page getDefeatOrderLog(@ModelAttribute DefeatOrderReq defeatOrderReq) {
        return transactionService.getDefeatOrderLog(defeatOrderReq);
    }

    @GetMapping("v1/transaction-order")
    public Page getTransactionOrder(@ModelAttribute TransactionOrderListReq transactionOrderReq) {
        return transactionService.getTransactionOrder(transactionOrderReq);
    }


    @GetMapping("v1/gas_transaction_log")
    public Page getGasTransactionLog(@ModelAttribute GasTransactionLogReq transactionLogReq) {
        return transactionService.getGasTransactionLog(transactionLogReq);

    }


    @GetMapping("v1/user-coin-log")
    public Page getUserCoinLog(@ModelAttribute UserCoinLogReq userCoinLogReq) {
        return transactionService.getUserCoinLog(userCoinLogReq);

    }


    @GetMapping("v1/platform-transfer")
    public Page getPlatformTransfer(@ModelAttribute PlatformTransferReq platformTransferReq) {

        return transactionService.getPlatformTransfer(platformTransferReq);
    }


}
