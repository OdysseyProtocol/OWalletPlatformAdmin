package com.stormfives.admin.wallet.dao;

import com.stormfives.admin.wallet.dao.entity.MerchantInfo;
import com.stormfives.admin.wallet.dao.entity.MerchantInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MerchantInfoMapper {
    int countByExample(MerchantInfoExample example);

    int deleteByExample(MerchantInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MerchantInfo record);

    int insertSelective(MerchantInfo record);

    List<MerchantInfo> selectByExample(MerchantInfoExample example);

    MerchantInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MerchantInfo record, @Param("example") MerchantInfoExample example);

    int updateByExample(@Param("record") MerchantInfo record, @Param("example") MerchantInfoExample example);

    int updateByPrimaryKeySelective(MerchantInfo record);

    int updateByPrimaryKey(MerchantInfo record);
}