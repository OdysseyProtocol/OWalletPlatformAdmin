package com.stormfives.admin.wallet.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.stormfives.admin.common.config.InitConfig;
import com.stormfives.admin.common.constant.Constants;
import com.stormfives.admin.common.dao.AdminOperationLogMapper;
import com.stormfives.admin.common.dao.entity.AdminOperationLogWithBLOBs;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.common.util.AES;
import com.stormfives.admin.common.util.RandomStringUtils;
import com.stormfives.admin.token.domain.Page;
import com.stormfives.admin.wallet.controller.req.MerchantInfoReq;
import com.stormfives.admin.wallet.controller.req.WalletGroupDetailReq;
import com.stormfives.admin.wallet.controller.req.WalletGroupReq;
import com.stormfives.admin.wallet.controller.response.MerchantWalletGroupVo;
import com.stormfives.admin.wallet.controller.response.WalletGroupDetailVo;
import com.stormfives.admin.wallet.controller.response.WalletGroupVo;
import com.stormfives.admin.wallet.dao.*;
import com.stormfives.admin.wallet.dao.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/16
 */
@Service
public class WalletService {


    @Autowired
    private MerchantInfoMapper merchantInfoMapper;

    @Autowired
    private MerchantWalletGroupRelationMapper merchantWalletGroupRelationMapper;

    @Autowired
    private WalletGroupMapper walletGroupMapper;

    @Autowired
    private WalletGroupDetailMapper walletGroupDetailMapper;

    @Autowired
    private WalletGroupCoinBalanceMapper walletGroupCoinBalanceMapper;


    @Autowired
    private AdminOperationLogMapper adminOperationLogMapper;


    @Autowired
    private InitConfig initConfig;

    /**
     *
     *
     * @param merchantInfoReq
     * @return
     */
    public Page getMerchantInfoList(MerchantInfoReq merchantInfoReq) {

        PageHelper.startPage(merchantInfoReq.getPageNum(), Constants.PAGE_SIZE);

        MerchantInfoExample merchantInfoExample = new MerchantInfoExample();
        MerchantInfoExample.Criteria or = merchantInfoExample.or();

        merchantInfoExample.setOrderByClause("id desc");

        List<MerchantInfo> merchantInfoList = merchantInfoMapper.selectByExample(merchantInfoExample);


        if (merchantInfoList != null && merchantInfoList.size() > 0) {

            for (MerchantInfo m : merchantInfoList) {

                List<MerchantWalletGroupRelation> walletGroupList = getWalletGroupRelationByMerchantId(m.getId());


            }
        }

        return Page.toPage(merchantInfoList);
    }


    /**
     *
     *
     * @param merchantInfo
     * @param adminId
     * @return
     * @throws InvalidArgumentException
     */
    public boolean addMerchantInfo(MerchantInfo merchantInfo, Integer adminId) throws InvalidArgumentException {

        if (StringUtils.isBlank(merchantInfo.getMerchantName()))
            throw new InvalidArgumentException(" !");

        merchantInfo.setCreatedBy(adminId);

        merchantInfo.setUpdatedBy(adminId);


        String apiKey = RandomStringUtils.getRandomString(30);

        merchantInfo.setApikey(apiKey);
        String secretKey = RandomStringUtils.getRandomString(16);

        merchantInfo.setSecurity(secretKey);

        int i = merchantInfoMapper.insertSelective(merchantInfo);


        return true;

    }


    /**
     *
     * @param
     * @param adminId
     * @return
     * @throws InvalidArgumentException
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean editMerchantInfo(MerchantInfoReq merchantInfoReq, Integer adminId) throws InvalidArgumentException, InvocationTargetException, IllegalAccessException {

        if (StringUtils.isBlank(merchantInfoReq.getMerchantName()))
            throw new InvalidArgumentException(" !");

        merchantInfoReq.setUpdatedBy(adminId);


        List<MerchantWalletGroupRelation> relationList = getWalletGroupRelationByMerchantId(merchantInfoReq.getId());

        deleteGroupByMerchantId(merchantInfoReq.getId());

        MerchantInfo merchantInfo = new MerchantInfo();

        merchantInfo.setSecurity(merchantInfoReq.getSecurity());
        merchantInfo.setRechargeSuccessUrl(merchantInfoReq.getRechargeSuccessUrl());
        merchantInfo.setTransferCallBackUrl(merchantInfoReq.getTransferCallBackUrl());
        merchantInfo.setUpdatedBy(adminId);
        merchantInfo.setApikey(merchantInfoReq.getApikey());
        merchantInfo.setMerchantName(merchantInfoReq.getMerchantName());
        merchantInfo.setId(merchantInfoReq.getId());
        merchantInfoMapper.updateByPrimaryKeySelective(merchantInfo);

        if (merchantInfoReq.getList() != null && merchantInfoReq.getList().size() > 0) {
            for (WalletGroup wgd : merchantInfoReq.getList()) {
                MerchantWalletGroupRelation relation = new MerchantWalletGroupRelation();
                relation.setMerchantId(merchantInfoReq.getId());
                relation.setWalletGroupId(wgd.getId());
                relation.setUpdatedBy(adminId);
                merchantWalletGroupRelationMapper.insertSelective(relation);
            }

        }


        return true;

    }


    /**
     *
     *
     * @param walletGroupReq
     * @param adminId
     * @return
     * @throws InvalidArgumentException
     */
    public boolean addWalletGroup(WalletGroupReq walletGroupReq, Integer adminId) throws InvalidArgumentException {

        if (StringUtils.isBlank(walletGroupReq.getGroupName()))
            throw new InvalidArgumentException(" !");

        if (walletGroupReq.getGroupType() == null)
            throw new InvalidArgumentException(" !");


        WalletGroup walletGroupByGroupName = getWalletGroupByGroupName(walletGroupReq.getGroupName());

        if (walletGroupByGroupName != null)
            throw new InvalidArgumentException(" !");


        WalletGroup walletGroup = new WalletGroup();
        walletGroup.setCreatedBy(adminId);
        walletGroup.setUpdatedBy(adminId);
        walletGroup.setGroupName(walletGroupReq.getGroupName().trim());
        walletGroup.setGroupType(walletGroupReq.getGroupType());


        int i = walletGroupMapper.insertSelective(walletGroup);

        return true;
    }

    /**
     *
     *
     * @param groupName
     * @return
     */
    public WalletGroup getWalletGroupByGroupName(String groupName) {

        WalletGroupExample example = new WalletGroupExample();
        example.or().andGroupNameEqualTo(groupName);

        List<WalletGroup> walletGroupList = walletGroupMapper.selectByExample(example);

        if (walletGroupList != null && walletGroupList.size() > 0) {
            return walletGroupList.get(0);
        }

        return null;

    }


    /**
     *
     *
     * @param walletGroupReq
     * @param adminId
     * @return
     * @throws InvalidArgumentException
     */

    public boolean editWalletGroup(WalletGroupReq walletGroupReq, Integer adminId) throws InvalidArgumentException, InvocationTargetException, IllegalAccessException {

        if (StringUtils.isBlank(walletGroupReq.getGroupName()))
            throw new InvalidArgumentException(" !");

        WalletGroup walletGroup = getWalletGroupById(walletGroupReq.getId());

        if (walletGroup == null)
            throw new InvalidArgumentException(" !");

        List<WalletGroup> walletGroupList = getWalletGroup(walletGroupReq.getGroupName().trim());

        if (walletGroupList != null && walletGroupList.size() > 0) {

            for (WalletGroup w : walletGroupList) {
                if (walletGroupReq.getGroupName().equalsIgnoreCase(w.getGroupName())) {
                    if (w.getId().intValue() != walletGroupReq.getId().intValue())
                        throw new InvalidArgumentException(" !");
                }

            }
        }

        WalletGroup newWalletGroup = new WalletGroup();
        newWalletGroup.setGroupName(walletGroupReq.getGroupName());
        newWalletGroup.setId(walletGroupReq.getId());
        newWalletGroup.setGroupType(walletGroupReq.getGroupType());

        int i = walletGroupMapper.updateByPrimaryKeySelective(newWalletGroup);



        return i == 1 ? true : false;
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean removeGroupDetail(WalletGroupReq walletGroupReq, Integer adminId) throws InvalidArgumentException {
        WalletGroupDetailExample walletGroupDetailExample = new WalletGroupDetailExample();
        WalletGroupDetailExample.Criteria criteria = walletGroupDetailExample.createCriteria();

        if (walletGroupReq.getGroupId() == null) {
            throw new InvalidArgumentException(" !");
        } else {
            criteria.andGroupIdEqualTo(walletGroupReq.getGroupId());
        }

        if (StringUtils.isBlank(walletGroupReq.getCoinAddress())) {
            throw new InvalidArgumentException(" ");
        } else {
            criteria.andWalletAddressEqualTo(walletGroupReq.getCoinAddress());
        }

        List<WalletGroupDetail> walletGroupDetails = walletGroupDetailMapper.selectByExample(walletGroupDetailExample);

        if(walletGroupDetails==null ||walletGroupDetails.size()<=0){
            throw new InvalidArgumentException(" !");

        }

        int i = walletGroupDetailMapper.deleteByExample(walletGroupDetailExample);

        return i == 1 ? true : false;

    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addGroupDetail(WalletGroupDetailReq detailReq, Integer adminId) throws InvalidArgumentException {

        WalletGroup walletGroup = getWalletGroupById(detailReq.getGroupId());

        if (walletGroup == null)
            throw new InvalidArgumentException(" !");
        if (detailReq.getPrivateKey() == null)
            throw new InvalidArgumentException(" !");
        if (detailReq.getWalletAddress() == null)
            throw new InvalidArgumentException(" !");


        WalletGroupDetail groupDetailByDetail = getGroupDetailByDetail(detailReq);
        if (groupDetailByDetail != null) {
            throw new InvalidArgumentException(" !");
        }

        if (1 == detailReq.getGroupType() && StringUtils.isEmpty(detailReq.getPrivateKey())) {
            throw new InvalidArgumentException("   !");
        }

        try {
            if (1 == detailReq.getGroupType()) {
                String encrypt = AES.encrypt(detailReq.getPrivateKey(), initConfig.deskey);
                detailReq.setPrivateKey(encrypt);
            }
        } catch (Exception e) {
            throw new InvalidArgumentException(" !");
        }

        detailReq.setCreatedBy(adminId);

        walletGroupDetailMapper.insertSelective(detailReq);

        return true;

    }


    /**
     *
     * @param merchantId
     * @return
     */
    public List<MerchantWalletGroupRelation> getWalletGroupRelationByMerchantId(Integer merchantId) {

        MerchantWalletGroupRelationExample example = new MerchantWalletGroupRelationExample();

        example.or().andMerchantIdEqualTo(merchantId);

        List<MerchantWalletGroupRelation> merchantWalletGroupRelationList = merchantWalletGroupRelationMapper.selectByExample(example);


        return merchantWalletGroupRelationList;


    }

    /**
     *
     *
     * @param id
     */
    public void deleteGroupByMerchantId(Integer id) {

        MerchantWalletGroupRelationExample example = new MerchantWalletGroupRelationExample();

        example.or().andMerchantIdEqualTo(id);

        merchantWalletGroupRelationMapper.deleteByExample(example);

    }

    /**
     *
     * @param id
     * @return
     */
    public WalletGroup getWalletGroupById(Integer id) {

        return walletGroupMapper.selectByPrimaryKey(id);
    }


    /**
     *
     */

    public WalletGroupDetail getGroupDetailByDetail(WalletGroupDetail walletGroupDetail) {
        WalletGroupDetailExample walletGroupDetailExample = new WalletGroupDetailExample();
        WalletGroupDetailExample.Criteria criteria = walletGroupDetailExample.createCriteria();
        criteria.andGroupIdEqualTo(walletGroupDetail.getGroupId());
        criteria.andWalletAddressEqualTo(walletGroupDetail.getWalletAddress());

        List<WalletGroupDetail> walletGroupDetails = walletGroupDetailMapper.selectByExample(walletGroupDetailExample);

        return walletGroupDetails == null || walletGroupDetails.size() != 1 ? null : walletGroupDetails.get(0);
    }

    /**
     *
     * @param groupName
     * @return
     */
    public List<WalletGroup> getWalletGroup(String groupName) {

        WalletGroupExample example = new WalletGroupExample();

        example.or().andGroupNameEqualTo(groupName);

        List<WalletGroup> walletGroupList = walletGroupMapper.selectByExample(example);

        return walletGroupList;

    }


    public Page getWalletGroupList(WalletGroupReq walletGroupReq) throws InvocationTargetException, IllegalAccessException {

        PageHelper.startPage(walletGroupReq.getPageNum(), Constants.PAGE_SIZE);


        List<WalletGroupVo> walletGroupVoList = walletGroupMapper.selectListByFilter(walletGroupReq);


        Page page = Page.toPage(walletGroupVoList);
        if (walletGroupVoList != null && walletGroupVoList.size() > 0) {

            for (WalletGroupVo wg : walletGroupVoList) {

                List<WalletGroupDetailVo> detailList = getWalletGroupDetailByGroupId(wg.getId());

                double balanceCount = 0;

                int walletCount = 0;

                String platforms = getWalletGroupPlatforms(wg.getId());

                if (detailList != null && detailList.size() > 0) {
                    for (WalletGroupDetailVo wv : detailList) {
                        balanceCount += wv.getBalance();
                        walletCount++;

                        List<WalletGroupCoinBalance> balanceList = getWalletBalanceByWalletId(wv.getId());
                        wv.setBalanceList(balanceList);
                    }
                }

                wg.setBalanceCount(balanceCount);

                wg.setWalletCount(walletCount);

                wg.setPlatforms(platforms);

                wg.setList(detailList);

            }
        }

        page.setList(walletGroupVoList);

        return page;
    }

    /**
     *
     * @param
     * @return
     */
    public List<WalletGroupCoinBalance> getWalletBalanceByWalletId(Integer walletId) {

        WalletGroupCoinBalanceExample walletGroupCoinBalanceExample = new WalletGroupCoinBalanceExample();

        walletGroupCoinBalanceExample.or().andWalletIdEqualTo(walletId);

        List<WalletGroupCoinBalance> walletGroupCoinBalances =
                walletGroupCoinBalanceMapper.selectByExample(walletGroupCoinBalanceExample);
        return walletGroupCoinBalances;
    }

    public String getWalletGroupPlatforms(Integer groupId) {

        MerchantWalletGroupRelationExample example = new MerchantWalletGroupRelationExample();
        example.or().andWalletGroupIdEqualTo(groupId);

        List<MerchantWalletGroupRelation> relationList = merchantWalletGroupRelationMapper.selectByExample(example);

        StringBuffer platforms = new StringBuffer();
        if (relationList != null && relationList.size() > 0) {
            for (MerchantWalletGroupRelation mwgr : relationList) {

                MerchantInfo merchantInfo = merchantInfoMapper.selectByPrimaryKey(mwgr.getMerchantId());
                platforms.append(merchantInfo.getMerchantName()).append(",");
            }
        }
        if (platforms.indexOf(",") > 0)
            platforms.delete(platforms.length() - 1, platforms.length());
        return platforms.toString();

    }

    public List<WalletGroupDetailVo> getWalletGroupDetailByGroupId(Integer groupId) throws InvocationTargetException, IllegalAccessException {

        WalletGroupDetailExample example = new WalletGroupDetailExample();
        example.or().andGroupIdEqualTo(groupId);

        List<WalletGroupDetail> walletGroupDetailList = walletGroupDetailMapper.selectByExample(example);

        List<WalletGroupDetailVo> list = new ArrayList<>();
        for (WalletGroupDetail w : walletGroupDetailList) {
            WalletGroupDetailVo walletGroupDetailVo = new WalletGroupDetailVo();
            BeanUtils.copyProperties(walletGroupDetailVo, w);
            list.add(walletGroupDetailVo);
        }
        return list;

    }

    /**
     *
     * @param walletGroupReq
     * @param adminId
     * @return
     * @throws InvalidArgumentException
     */
    public boolean deleteWalletGroup(WalletGroupReq walletGroupReq, Integer adminId) throws InvalidArgumentException {


        int i = walletGroupMapper.deleteByPrimaryKey(walletGroupReq.getGroupId());

        WalletGroupDetailExample example = new WalletGroupDetailExample();
        example.or().andGroupIdEqualTo(walletGroupReq.getGroupId());
        WalletGroupDetail walletGroupDetail = new WalletGroupDetail();
        walletGroupDetail.setGroupId(walletGroupReq.getGroupId());
        walletGroupDetailMapper.updateByExampleSelective(walletGroupDetail, example);


        return false;
    }


    public List<WalletGroup> getWalletListAll() {

        WalletGroupExample example = new WalletGroupExample();
        List<WalletGroup> walletGroups = walletGroupMapper.selectByExample(example);
        return walletGroups;
    }

    /**
     *
     * @param merchantInfo
     * @param adminId
     * @return
     * @throws InvalidArgumentException
     */
    public boolean deleteMerchantInfo(MerchantInfoReq merchantInfo, Integer adminId) throws InvalidArgumentException {



        MerchantWalletGroupRelationExample merchantWalletGroupRelationExample = new MerchantWalletGroupRelationExample();

        merchantWalletGroupRelationExample.or().andWalletGroupIdEqualTo(merchantInfo.getGroupId()).andMerchantIdEqualTo(merchantInfo.getId());
        List<MerchantWalletGroupRelation> merchantWalletGroupRelations = merchantWalletGroupRelationMapper.selectByExample(merchantWalletGroupRelationExample);

        merchantWalletGroupRelationMapper.deleteByExample(merchantWalletGroupRelationExample);


        AdminOperationLogWithBLOBs adminOperationLog = new AdminOperationLogWithBLOBs();
        if (merchantWalletGroupRelations != null && merchantWalletGroupRelations.size() > 0) {
            adminOperationLog.setCreatedBy(adminId);
            adminOperationLog.setUpdatedBy(adminId);
            adminOperationLog.setTableName("MerchantWalletGroupRelation");
            adminOperationLog.setOldValue(JSON.toJSONString(merchantWalletGroupRelations.get(0)));
            adminOperationLog.setNewValue("");
            adminOperationLog.setActionType(2);
            adminOperationLog.setValueType(MerchantWalletGroupRelation.class.getTypeName());

            adminOperationLogMapper.insertSelective(adminOperationLog);
        }


        return true;


    }

    public List<WalletGroup> getOtherWalletGroupList(WalletGroupReq walletGroupReq) {

        MerchantWalletGroupRelationExample merchantWalletGroupRelationExample = new MerchantWalletGroupRelationExample();
        merchantWalletGroupRelationExample.or().andMerchantIdEqualTo(walletGroupReq.getMerchantId());

        List<MerchantWalletGroupRelation> merchantWalletGroupRelations = merchantWalletGroupRelationMapper.selectByExample(merchantWalletGroupRelationExample);


        MerchantWalletGroupVo merchantWalletGroupVo = new MerchantWalletGroupVo();
        WalletGroupExample example = new WalletGroupExample();
        example.or().andGroupTypeEqualTo(walletGroupReq.getGroupType());


        List<WalletGroup> walletGroupList = walletGroupMapper.selectByExample(example);
        if (walletGroupList != null && walletGroupList.size() > 0) {
            Iterator<WalletGroup> iterator = walletGroupList.iterator();

            while (iterator.hasNext()) {
                WalletGroup wg = iterator.next();
                if (merchantWalletGroupRelations != null && merchantWalletGroupRelations.size() > 0) {
                    for (MerchantWalletGroupRelation mwgr : merchantWalletGroupRelations) {
                        if (mwgr.getWalletGroupId().intValue() == wg.getId().intValue()) {
                            iterator.remove();
                            break;

                        }
                    }
                }
            }


        }

        return walletGroupList;
    }

    public boolean merchantAddWalletGroup(WalletGroupReq walletGroupReq, Integer adminId) throws InvalidArgumentException {


        if (walletGroupReq.getGroupId() == null)
            throw new InvalidArgumentException("");

        if (walletGroupReq.getMerchantId() == null)
            throw new InvalidArgumentException("");

        MerchantInfo merchantInfo = merchantInfoMapper.selectByPrimaryKey(walletGroupReq.getMerchantId());
        if (merchantInfo == null)
            throw new InvalidArgumentException("");
        WalletGroup walletGroup = walletGroupMapper.selectByPrimaryKey(walletGroupReq.getGroupId());
        if (walletGroup == null)
            throw new InvalidArgumentException("");

        MerchantWalletGroupRelationExample merchantWalletGroupRelationExample = new MerchantWalletGroupRelationExample();
        merchantWalletGroupRelationExample.or().andMerchantIdEqualTo(walletGroupReq.getMerchantId()).andWalletGroupIdEqualTo(walletGroupReq.getGroupId());

        List<MerchantWalletGroupRelation> merchantWalletGroupRelations = merchantWalletGroupRelationMapper.selectByExample(merchantWalletGroupRelationExample);
        if (merchantWalletGroupRelations != null && merchantWalletGroupRelations.size() > 0)
            throw new InvalidArgumentException(" ");

        MerchantWalletGroupRelation merchantWalletGroupRelation = new MerchantWalletGroupRelation();
        merchantWalletGroupRelation.setWalletGroupId(walletGroupReq.getGroupId());
        merchantWalletGroupRelation.setMerchantId(walletGroupReq.getMerchantId());
        merchantWalletGroupRelation.setUpdatedBy(adminId);
        merchantWalletGroupRelation.setCreatedBy(adminId);

        merchantWalletGroupRelationMapper.insertSelective(merchantWalletGroupRelation);

        return true;
    }

    public List<WalletGroup> getWalletGroupByMerchantId(Integer id) {
        MerchantWalletGroupRelationExample merchantWalletGroupRelationExample = new MerchantWalletGroupRelationExample();
        merchantWalletGroupRelationExample.or().andMerchantIdEqualTo(id);
        List<MerchantWalletGroupRelation> merchantWalletGroupRelations = merchantWalletGroupRelationMapper.selectByExample(merchantWalletGroupRelationExample);
        List<WalletGroup> walletGroupList = new ArrayList<>();

        if (merchantWalletGroupRelations != null && merchantWalletGroupRelations.size() > 0) {
            for (MerchantWalletGroupRelation mwgr : merchantWalletGroupRelations) {
                WalletGroup walletGroup = walletGroupMapper.selectByPrimaryKey(mwgr.getWalletGroupId());
                if (walletGroup != null)
                    walletGroupList.add(walletGroup);
            }
        }
        return walletGroupList;
    }

    public List<MerchantInfo> getMerchantInfoListNopage() {


        MerchantInfoExample merchantInfoExample = new MerchantInfoExample();
        MerchantInfoExample.Criteria or = merchantInfoExample.or();

        merchantInfoExample.setOrderByClause("id desc");

        List<MerchantInfo> merchantInfoList = merchantInfoMapper.selectByExample(merchantInfoExample);


        if (merchantInfoList != null && merchantInfoList.size() > 0) {

            for (MerchantInfo m : merchantInfoList) {

                List<MerchantWalletGroupRelation> walletGroupList = getWalletGroupRelationByMerchantId(m.getId());


            }
        }

        return merchantInfoList;
    }
}
