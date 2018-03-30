package com.stormfives.admin.transaction.service;

import com.github.pagehelper.PageHelper;
import com.stormfives.admin.common.constant.Constants;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.token.domain.Page;
import com.stormfives.admin.transaction.controller.req.*;
import com.stormfives.admin.transaction.controller.resp.TransactionOrderVo;
import com.stormfives.admin.transaction.dao.DefeatOrderLogMapper;
import com.stormfives.admin.transaction.dao.GasTransactionLogMapper;
import com.stormfives.admin.transaction.dao.PlatformTransferMapper;
import com.stormfives.admin.transaction.dao.TransactionOrderMapper;
import com.stormfives.admin.transaction.dao.entity.*;
import com.stormfives.admin.user.dao.UserCoinLogMapper;
import com.stormfives.admin.user.dao.entity.UserCoinLog;
import com.stormfives.admin.user.dao.entity.UserCoinLogExample;
import com.stormfives.admin.wallet.dao.MerchantInfoMapper;
import com.stormfives.admin.wallet.dao.entity.MerchantInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.stormfives.admin.common.util.DateUtils.timeStamp2Date;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/21
 */
@Service
public class TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionService.class);


    @Autowired
    private TransactionOrderMapper transactionOrderMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private DefeatOrderLogMapper defeatOrderLogMapper;


    @Autowired
    private GasTransactionLogMapper gasTransactionLogMapper;


    @Autowired
    private UserCoinLogMapper userCoinLogMapper;


    @Autowired
    private PlatformTransferMapper platformTransferMapper;


    public Page getRechargeList(TransactionOrderReq transactionOrderReq) {

        transactionOrderReq.setTransactionType(1);
        List<TransactionOrderVo> list = getTransactionOrderVos(transactionOrderReq);

        return Page.toPage(list);
    }

    public Page getTransactionList(TransactionOrderReq transactionOrderReq) {
        PageHelper.startPage(transactionOrderReq.getPageNum(), Constants.PAGE_SIZE);

        transactionOrderReq.setTransactionType(4);
        List<TransactionOrderVo> list = getTransactionOrderVos(transactionOrderReq);

        return Page.toPage(list);
    }

    private List<TransactionOrderVo> getTransactionOrderVos(TransactionOrderReq transactionOrderReq) {
        PageHelper.startPage(transactionOrderReq.getPageNum(), Constants.PAGE_SIZE);

        List<TransactionOrderVo> list = transactionOrderMapper.selectListByFilter(transactionOrderReq);

        if (list != null && list.size() > 0) {
            for (TransactionOrderVo t : list) {
                MerchantInfo merchantInfo = merchantInfoMapper.selectByPrimaryKey(t.getMerchantId());
                if (merchantInfo != null)
                    t.setMerchantName(merchantInfo.getMerchantName());
            }
        }
        return list;
    }


    public void confirmOrder(String txHash, Integer adminId) throws InvalidArgumentException {

        if (StringUtils.isBlank(txHash))
            throw new InvalidArgumentException(" ");
        TransactionOrder transactionOrder = transactionOrderMapper.selectByPrimaryKey(txHash);
        if (transactionOrder == null)
            throw new InvalidArgumentException(" ");
        if (transactionOrder.getOrderStatus() != 2) {
            TransactionOrder transactionOrder1 = new TransactionOrder();
            transactionOrder1.setOrderStatus(2);
            transactionOrder1.setTxHash(txHash);
            transactionOrderMapper.updateByPrimaryKeySelective(transactionOrder1);
        }

    }


    public Page getDefeatOrderLog(DefeatOrderReq defeatOrderReq) {


        PageHelper.startPage(defeatOrderReq.getPageNum(), Constants.PAGE_SIZE);

        DefeatOrderLogExample example = new DefeatOrderLogExample();
        DefeatOrderLogExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(defeatOrderReq.getTxHash())) {
            criteria.andTxHashLike(defeatOrderReq.getTxHash() + "%");
        }

        if (defeatOrderReq.getId() != null) {
            criteria.andIdEqualTo(defeatOrderReq.getId());
        }


        try {
            if (!StringUtils.isEmpty(defeatOrderReq.getBeginTime()) && !StringUtils.isEmpty(defeatOrderReq.getEndTime())) {
                criteria.andCreateTimeBetween(timeStamp2Date(defeatOrderReq.getBeginTime()), timeStamp2Date(defeatOrderReq.getEndTime()));
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        example.setOrderByClause("id desc");


        List<DefeatOrderLog> list = defeatOrderLogMapper.selectByExample(example);


        return Page.toPage(list);

    }


    /**
     * 交易订单查询
     *
     * @param transactionOrderReq
     * @return
     */
    public Page getTransactionOrder(TransactionOrderListReq transactionOrderReq) {

        PageHelper.startPage(transactionOrderReq.getPageNum(), Constants.PAGE_SIZE);
        TransactionOrderExample example = new TransactionOrderExample();
        TransactionOrderExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(transactionOrderReq.getTxHash())) {
            criteria.andTxHashLike(transactionOrderReq.getTxHash() + "%");
        }

        if (transactionOrderReq.getOrderStatus() != null && transactionOrderReq.getOrderStatus() > 0 && transactionOrderReq.getOrderStatus() < 4) {
            criteria.andOrderStatusEqualTo(transactionOrderReq.getOrderStatus());
        }


        if (transactionOrderReq.getCoinId() != null) {
            criteria.andCoinIdEqualTo(transactionOrderReq.getCoinId());
        }

        if (!StringUtils.isEmpty(transactionOrderReq.getCoinName())) {
            criteria.andCoinNameLike(transactionOrderReq.getCoinName() + "%");
        }


        if (transactionOrderReq.getTranscationType() != null) {
            criteria.andTranscationTypeEqualTo(transactionOrderReq.getTranscationType());
        }


        try {
            if (!StringUtils.isEmpty(transactionOrderReq.getBeginTradingTime()) && !StringUtils.isEmpty(transactionOrderReq.getEndTradingTime())) {
                criteria.andTradingTimeBetween(timeStamp2Date(transactionOrderReq.getBeginTradingTime()), timeStamp2Date(transactionOrderReq.getEndTradingTime()));
            }
        } catch (Exception e) {
            logger.error("", e);
        }


        example.setOrderByClause("trading_time desc");


        List<TransactionOrder> transactionOrders = transactionOrderMapper.selectByExample(example);

        return Page.toPage(transactionOrders);

    }


    /**
     * @param transactionLogReq
     * @return
     */
    public Page getGasTransactionLog(GasTransactionLogReq transactionLogReq) {

        GasTransactionLogExample example = new GasTransactionLogExample();
        GasTransactionLogExample.Criteria criteria = example.createCriteria();

        PageHelper.startPage(transactionLogReq.getPageNum(), Constants.PAGE_SIZE);


        try {
            if (!StringUtils.isEmpty(transactionLogReq.getBeginTime()) && !StringUtils.isEmpty(transactionLogReq.getEndTime())) {
                criteria.andCreatedAtBetween(timeStamp2Date(transactionLogReq.getBeginTime()), timeStamp2Date(transactionLogReq.getEndTime()));
            }
        } catch (Exception e) {
            logger.error("", e);
        }


        if (!StringUtils.isEmpty(transactionLogReq.getOrderTxHash())) {
            criteria.andOrderTxHashLike(transactionLogReq.getOrderTxHash() + "%");
        }

        if (!StringUtils.isEmpty(transactionLogReq.getFromAddress())) {
            criteria.andFromAddressLike(transactionLogReq.getOrderTxHash() + "%");
        }

        if (!StringUtils.isEmpty(transactionLogReq.getToAddress())) {
            criteria.andToAddressLike(transactionLogReq.getToAddress() + "%");
        }

        example.setOrderByClause("id desc");


        List<GasTransactionLog> gasTransactionLogs = gasTransactionLogMapper.selectByExample(example);

        return Page.toPage(gasTransactionLogs);

    }


    public Page getUserCoinLog(UserCoinLogReq userCoinLogReq) {

        UserCoinLogExample example = new UserCoinLogExample();
        UserCoinLogExample.Criteria criteria = example.createCriteria();

        PageHelper.startPage(userCoinLogReq.getPageNum(), Constants.PAGE_SIZE);

        if (!StringUtils.isEmpty(userCoinLogReq.getOrderTxHash())) {
            criteria.andOrderTxHashLike(userCoinLogReq.getOrderTxHash() + "%");
        }

        if (userCoinLogReq.getMerchantId() != null) {
            criteria.andMerchantIdEqualTo(userCoinLogReq.getMerchantId());
        }

        if (userCoinLogReq.getUserid() != null) {
            criteria.andUseridEqualTo(userCoinLogReq.getUserid());
        }

        if (userCoinLogReq.getChangeType() != null) {
            criteria.andChangeTypeEqualTo(userCoinLogReq.getChangeType());
        }


        try {
            if (!StringUtils.isEmpty(userCoinLogReq.getBeginTime()) && !StringUtils.isEmpty(userCoinLogReq.getEndTime())) {
                criteria.andCreateTimeBetween(timeStamp2Date(userCoinLogReq.getBeginTime()), timeStamp2Date(userCoinLogReq.getEndTime()));
            }
        } catch (Exception e) {
            logger.error("", e);
        }


        if (userCoinLogReq.getCoinId() != null) {
            criteria.andCoinIdEqualTo(userCoinLogReq.getCoinId());
        }

        if (!StringUtils.isEmpty(userCoinLogReq.getCoinName())) {
            criteria.andCoinNameLike(userCoinLogReq.getCoinName() + "%");
        }

        example.setOrderByClause("id desc");


        List<UserCoinLog> userCoinLogs = userCoinLogMapper.selectByExample(example);

        return Page.toPage(userCoinLogs);
    }


    public Page getPlatformTransfer(PlatformTransferReq platformTransferReq) {
        PlatformTransferExample platformTransferExample = new PlatformTransferExample();
        PlatformTransferExample.Criteria criteria = platformTransferExample.createCriteria();

        PageHelper.startPage(platformTransferReq.getPageNum(), Constants.PAGE_SIZE);

        if (!StringUtils.isEmpty(platformTransferReq.getOrderTxHash())) {
            criteria.andOrderTxHashLike(platformTransferReq.getOrderTxHash() + "%");
        }


        if (platformTransferReq.getStatus() != null && platformTransferReq.getStatus() >= 1 && platformTransferReq.getStatus() <= 2) {
            criteria.andStatusEqualTo(platformTransferReq.getStatus());
        }

        if (!StringUtils.isEmpty(platformTransferReq.getFromAddress())) {
            criteria.andFromAddressLike(platformTransferReq.getOrderTxHash() + "%");
        }

        if (!StringUtils.isEmpty(platformTransferReq.getToAddress())) {
            criteria.andToAddressLike(platformTransferReq.getToAddress() + "%");
        }


        if (platformTransferReq.getCoinId() != null) {
            criteria.andCoinIdEqualTo(platformTransferReq.getCoinId());
        }


        if (platformTransferReq.getMerchantId() != null) {
            criteria.andMerchantIdEqualTo(platformTransferReq.getMerchantId());
        }

        try {
            if (!StringUtils.isEmpty(platformTransferReq.getBeginTime()) && !StringUtils.isEmpty(platformTransferReq.getEndTime())) {
                criteria.andCreatedAtBetween(timeStamp2Date(platformTransferReq.getBeginTime()), timeStamp2Date(platformTransferReq.getEndTime()));
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        platformTransferExample.setOrderByClause("id desc");


        List<PlatformTransfer> platformTransfers = platformTransferMapper.selectByExample(platformTransferExample);

        return Page.toPage(platformTransfers);
    }
}
