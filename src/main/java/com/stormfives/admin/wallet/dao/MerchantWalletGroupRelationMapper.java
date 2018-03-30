package com.stormfives.admin.wallet.dao;

import com.stormfives.admin.wallet.dao.entity.MerchantWalletGroupRelation;
import com.stormfives.admin.wallet.dao.entity.MerchantWalletGroupRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MerchantWalletGroupRelationMapper {
    int countByExample(MerchantWalletGroupRelationExample example);

    int deleteByExample(MerchantWalletGroupRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchantWalletGroupRelation record);

    int insertSelective(MerchantWalletGroupRelation record);

    List<MerchantWalletGroupRelation> selectByExample(MerchantWalletGroupRelationExample example);

    MerchantWalletGroupRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantWalletGroupRelation record, @Param("example") MerchantWalletGroupRelationExample example);

    int updateByExample(@Param("record") MerchantWalletGroupRelation record, @Param("example") MerchantWalletGroupRelationExample example);

    int updateByPrimaryKeySelective(MerchantWalletGroupRelation record);

    int updateByPrimaryKey(MerchantWalletGroupRelation record);
}