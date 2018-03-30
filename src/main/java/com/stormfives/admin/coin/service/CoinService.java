package com.stormfives.admin.coin.service;

import com.github.pagehelper.PageHelper;
import com.stormfives.admin.coin.controller.req.CoinReq;
import com.stormfives.admin.coin.dao.CoinInfoMapper;
import com.stormfives.admin.coin.dao.RechargeGatherRuleMapper;
import com.stormfives.admin.coin.dao.entity.CoinInfo;
import com.stormfives.admin.coin.dao.entity.CoinInfoExample;
import com.stormfives.admin.coin.dao.entity.RechargeGatherRule;
import com.stormfives.admin.coin.dao.entity.RechargeGatherRuleExample;
import com.stormfives.admin.common.constant.Constants;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.token.domain.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/15
 */
@Service
public class CoinService {


    @Autowired
    private CoinInfoMapper coinInfoMapper;

    @Autowired
    private RechargeGatherRuleMapper rechargeGatherRuleMapper;

    public Page getCoinList(CoinReq coinReq) {
        PageHelper.startPage(coinReq.getPageNum(), Constants.PAGE_SIZE);

        CoinInfoExample coinInfoExample = new CoinInfoExample();
        coinInfoExample.setOrderByClause("id desc");

        List<CoinInfo> coinInfoList = coinInfoMapper.selectByExample(coinInfoExample);


        return Page.toPage(coinInfoList);
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean addCoinInfo(String coinName, String contractAddress, String fullName, Integer adminId) throws InvalidArgumentException {

        CoinInfo coinInfo = new CoinInfo();

        if (StringUtils.isBlank(coinName))
            throw new InvalidArgumentException(" !");

        if (StringUtils.isBlank(fullName))
            throw new InvalidArgumentException(" !");

        if (StringUtils.isBlank(contractAddress))
            throw new InvalidArgumentException(" !");



        coinInfo.setCoinName(coinName.trim());
        coinInfo.setFullName(fullName.trim());
        coinInfo.setContractAddress(contractAddress.trim());
        coinInfo.setCreatedBy(adminId);
        coinInfo.setUpdatedBy(adminId);

        CoinInfo coinInfoByCoinName = getCoinInfoByCoinName(coinName.trim());
        if (coinInfoByCoinName != null )
            throw new InvalidArgumentException(" !");

        CoinInfo coinInfoByFullName = getCoinInfoByFullName(fullName.trim());
        if (coinInfoByFullName != null)
            throw new InvalidArgumentException(" !");

        CoinInfo coinInfoByContractAddress = getCoinInfoByContractAddress(contractAddress.trim());
        if (coinInfoByContractAddress != null)
            throw new InvalidArgumentException(" !");


        int coinId = coinInfoMapper.insertSelective(coinInfo);


        return true;

    }


    public boolean editCoinInfo(Integer id, String coinName, String contractAddress, String fullName,
                                Integer coinHigherLimit , Integer coinLowerLimit , Integer dayCycleRound,
                                Integer adminId) throws InvalidArgumentException {

        CoinInfo coinInfo = new CoinInfo();


        if (StringUtils.isBlank(coinName))
            throw new InvalidArgumentException("error!");

        if (StringUtils.isBlank(fullName))
            throw new InvalidArgumentException("error!");

        if (StringUtils.isBlank(contractAddress))
            throw new InvalidArgumentException("error!");

        CoinInfo oldCoinInfo = getCoinInfoById(id);

        if (oldCoinInfo == null)
            throw new InvalidArgumentException("error");


        CoinInfo coin1 = getCoinInfoByCoinName(coinName.trim());


        if (coin1!=null && coin1.getId().intValue()!= id.intValue())
            throw new InvalidArgumentException("error!");


        CoinInfo coin2 = getCoinInfoByFullName(fullName.trim());

        if (coin2!=null && coin2.getId().intValue()!= id.intValue())
            throw new InvalidArgumentException("error!");

        CoinInfo coin3 = getCoinInfoByContractAddress(contractAddress.trim());

        if (coin3!=null && coin3.getId().intValue()!= id.intValue())
            throw new InvalidArgumentException("error!");

        coinInfo.setId(id);
        coinInfo.setUpdatedBy(adminId);
        coinInfo.setContractAddress(contractAddress);
        coinInfo.setFullName(fullName);
        coinInfo.setCoinName(coinName);
        coinInfo.setCoinHigherLimit(coinHigherLimit);
        coinInfo.setCoinLowerLimit(coinLowerLimit);
        coinInfo.setDayCycleRound(dayCycleRound);
        coinInfoMapper.updateByPrimaryKeySelective(coinInfo);
        return true;

    }

    public CoinInfo getCoinInfoByContractAddress(String contractAddress) {
        CoinInfoExample coinInfoExample = new CoinInfoExample();
        coinInfoExample.or().andContractAddressEqualTo(contractAddress);
        coinInfoExample.setOrderByClause("id desc");
        List<CoinInfo> coinInfoList = coinInfoMapper.selectByExample(coinInfoExample);

        if (coinInfoList != null && coinInfoList.size()>0)
            return coinInfoList.get(0);

        return null;

    }


    public CoinInfo getCoinInfoByFullName(String fullName) {
        CoinInfoExample coinInfoExample = new CoinInfoExample();
        coinInfoExample.or().andFullNameEqualTo(fullName);
        coinInfoExample.setOrderByClause("id desc");
        List<CoinInfo> coinInfoList = coinInfoMapper.selectByExample(coinInfoExample);

        if (coinInfoList != null && coinInfoList.size()>0)
            return coinInfoList.get(0);

        return null;
    }

    public CoinInfo getCoinInfoByCoinName(String coinName) {
        CoinInfoExample coinInfoExample = new CoinInfoExample();
        coinInfoExample.or().andCoinNameEqualTo(coinName);
        coinInfoExample.setOrderByClause("id desc");
        List<CoinInfo> coinInfoList = coinInfoMapper.selectByExample(coinInfoExample);

        if (coinInfoList != null && coinInfoList.size()>0)
            return coinInfoList.get(0);

        return null;
    }

    public CoinInfo getCoinInfoById(Integer id) {

        CoinInfo coinInfo = coinInfoMapper.selectByPrimaryKey(id);

        return coinInfo;
    }

    public boolean editRechargeGatherRule(Integer coinId, Integer coinHigherLimit, Integer coinLowerLimit, Integer dayCycleRound, Integer adminId) throws InvalidArgumentException {

        RechargeGatherRule rechargeGatherRule = new RechargeGatherRule();

        RechargeGatherRule rule = getRechargeGatherRuleByCoidId(coinId);

        if (rule == null)
            throw new InvalidArgumentException("don't exits!");

        rechargeGatherRule.setCoinId(coinId);
        rechargeGatherRule.setCoinHigherLimit(coinHigherLimit);
        rechargeGatherRule.setCoinLowerLimit(coinLowerLimit);
        rechargeGatherRule.setDayCycleRound(dayCycleRound);
        rechargeGatherRule.setUpdatedBy(adminId);

        RechargeGatherRuleExample example = new RechargeGatherRuleExample();
        example.or().andCoinIdEqualTo(coinId);
        rechargeGatherRuleMapper.updateByExampleSelective(rechargeGatherRule,example);

        return true;
    }


    public RechargeGatherRule getRechargeGatherRuleByCoidId(Integer coinId) {

        RechargeGatherRuleExample example = new RechargeGatherRuleExample();
        example.or().andCoinIdEqualTo(coinId);
        example.setOrderByClause("id desc");
        List<RechargeGatherRule> gatherRuleList = rechargeGatherRuleMapper.selectByExample(example);

        if (gatherRuleList != null && gatherRuleList.size()>0)
            return gatherRuleList.get(0);

        return null;
    }

    public boolean deleteCoinInfo(Integer id, Integer adminId) throws InvalidArgumentException{


        return false;
    }
}
