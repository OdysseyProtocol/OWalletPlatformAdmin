package com.stormfives.admin.user.service;

import com.github.pagehelper.PageHelper;
import com.stormfives.admin.coin.dao.CoinInfoMapper;
import com.stormfives.admin.coin.dao.entity.CoinInfo;
import com.stormfives.admin.coin.dao.entity.CoinInfoExample;
import com.stormfives.admin.common.constant.Constants;
import com.stormfives.admin.common.response.FailResponse;
import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.common.response.SuccessResponse;
import com.stormfives.admin.token.domain.Page;
import com.stormfives.admin.user.controller.req.CoinBalanceReq;
import com.stormfives.admin.user.controller.req.UserWalletInfoReq;
import com.stormfives.admin.user.controller.reqp.UserInfoVo;
import com.stormfives.admin.user.dao.UserCoinBalanceMapper;
import com.stormfives.admin.user.dao.UserWalletInfoMapper;
import com.stormfives.admin.user.dao.entity.UserCoinBalance;
import com.stormfives.admin.user.dao.entity.UserCoinBalanceExample;
import com.stormfives.admin.user.dao.entity.UserWalletInfo;
import com.stormfives.admin.user.dao.entity.UserWalletInfoExample;
import com.stormfives.admin.wallet.dao.MerchantInfoMapper;
import com.stormfives.admin.wallet.dao.entity.MerchantInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.stormfives.admin.common.util.DateUtils.timeStamp2Date;

/**
 * Created by y on 2018/3/14.
 */

@Service
public class UserManagerService {

    Logger logger = LoggerFactory.getLogger(UserManagerService.class);

    @Autowired
    UserCoinBalanceMapper userCoinBalanceMapper;

    @Autowired
    CoinInfoMapper coinInfoMapper;

    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private UserWalletInfoMapper userWalletInfoMapper;


    /**
     * @param coinBalanceReq
     * @return
     */
    public Page selectByPage(CoinBalanceReq coinBalanceReq) {

        if (coinBalanceReq.getPageSize() == null) coinBalanceReq.setPageSize(10);


        PageHelper.startPage(coinBalanceReq.getPageNum(), coinBalanceReq.getPageSize());

        UserWalletInfoExample userWalletInfoExample = new UserWalletInfoExample();

        UserWalletInfoExample.Criteria criteria = userWalletInfoExample.or();


        if (coinBalanceReq.getMerchantId() != null) criteria.andMerchantIdEqualTo(coinBalanceReq.getMerchantId());

        List<UserWalletInfo> userWalletInfoList = userWalletInfoMapper.selectByExample(userWalletInfoExample);

        Page page = Page.toPage(userWalletInfoList);
        List<UserInfoVo> responseList = new ArrayList<>();
        if (userWalletInfoList != null && userWalletInfoList.size() > 0) {
            for (UserWalletInfo ucb : userWalletInfoList) {


                UserInfoVo userInfoVo = new UserInfoVo();
                BeanUtils.copyProperties(ucb, userInfoVo);

                UserCoinBalanceExample userCoinBalanceExample = new UserCoinBalanceExample();

                if (ucb.getUserid() == null)
                    continue;
                userCoinBalanceExample.or().andUseridEqualTo(ucb.getUserid());

                List<UserCoinBalance> userCoinBalances = userCoinBalanceMapper.selectByExample(userCoinBalanceExample);

                if (userCoinBalances != null && userCoinBalances.size() > 0) {
                    userInfoVo.setCoinBalance(userCoinBalances.get(0).getCoinBalance());
                    userInfoVo.setCoinId(userCoinBalances.get(0).getCoinId());
                    userInfoVo.setCoinName(userCoinBalances.get(0).getCoinName());
                    userInfoVo.setLastTradingTime(userCoinBalances.get(0).getLastTradingTime());

                }

                MerchantInfo merchantInfo = merchantInfoMapper.selectByPrimaryKey(ucb.getMerchantId());
                if (merchantInfo != null)
                    userInfoVo.setMerchantName(merchantInfo.getMerchantName());

                responseList.add(userInfoVo);
            }

        }

        page.setList(responseList);
        return page;
    }


    /**
     * @param userCoinBalance
     * @return
     */
    public int insertUserCoinBalance(UserCoinBalance userCoinBalance) {
        int insert = userCoinBalanceMapper.insertSelective(userCoinBalance);
        return insert;
    }

    /**
     * @param userCoinBalance
     * @return
     */
    public int updateByPrimaryKeySelective(UserCoinBalance userCoinBalance) {
        return userCoinBalanceMapper.updateByPrimaryKeySelective(userCoinBalance);
    }


    /**
     * 1004
     *
     * @return
     */
    public ResponseValue getCoinInfo() {
        CoinInfoExample coinInfoExample = new CoinInfoExample();
        List<CoinInfo> coinInfos = coinInfoMapper.selectByExample(coinInfoExample);
        ResponseValue responseValue = null;
        if (coinInfos == null || coinInfos.size() <= 0) {
            responseValue = new FailResponse(1004, "param error");
        } else {
            responseValue = new SuccessResponse(coinInfos);
        }
        return responseValue;

    }


    /**
     * @param userWalletInfo
     * @return
     */
    public Page getUserWalletInfo(UserWalletInfoReq userWalletInfo) {

        PageHelper.startPage(userWalletInfo.getPageNum(), Constants.PAGE_SIZE);


        UserWalletInfoExample userWalletInfoExample = new UserWalletInfoExample();

        UserWalletInfoExample.Criteria criteria = userWalletInfoExample.createCriteria();


        if (userWalletInfo.getCoinAddress() != null) {
            criteria.andCoinAddressLike("%"+userWalletInfo.getCoinAddress()+"%");
        }

        if (userWalletInfo.getMerchantId() != null) {
            criteria.andMerchantIdEqualTo(userWalletInfo.getMerchantId());
        }

        List<UserWalletInfo> userWalletInfos = userWalletInfoMapper.selectByExample(userWalletInfoExample);


        for (UserWalletInfo user : userWalletInfos) {
            user.setPrivatekey(null);
        }

        userWalletInfoExample.setOrderByClause("id desc");

        return Page.toPage(userWalletInfos);
    }


    /**
     * @param coinBalanceReq
     * @return
     */
    public Page getCoinBalances(CoinBalanceReq coinBalanceReq) {
        PageHelper.startPage(coinBalanceReq.getPageNum(), Constants.PAGE_SIZE);

        UserCoinBalanceExample userCoinBalanceExample = new UserCoinBalanceExample();


        UserCoinBalanceExample.Criteria criteria = userCoinBalanceExample.createCriteria();

        if (coinBalanceReq.getCoinId() != null) {
            criteria.andCoinIdEqualTo(coinBalanceReq.getCoinId());
        }

        if (coinBalanceReq.getMerchantId() != null) {
            criteria.andMerchantIdEqualTo(coinBalanceReq.getMerchantId());
        }

        if (coinBalanceReq.getTransferStatus() != null) {
            criteria.andTransferStatusEqualTo(coinBalanceReq.getTransferStatus());
        }


        if (coinBalanceReq.getCoinName() != null) {
            criteria.andCoinNameLike("%"+coinBalanceReq.getCoinName()+"%");
        }

        try {
            if (!StringUtils.isEmpty(coinBalanceReq.getBeginTime()) && !StringUtils.isEmpty(coinBalanceReq.getEndTime())) {
                criteria.andLastTradingTimeBetween(timeStamp2Date(coinBalanceReq.getBeginTime()), timeStamp2Date(coinBalanceReq.getEndTime()));
            }
        } catch (Exception e) {
            logger.error(" ", e);
        }

        userCoinBalanceExample.setOrderByClause("last_trading_time desc");

        List<UserCoinBalance> userCoinBalances = userCoinBalanceMapper.selectByExample(userCoinBalanceExample);

        return Page.toPage(userCoinBalances);

    }


}
