package com.stormfives.admin.coin.dao;

import com.stormfives.admin.coin.dao.entity.RechargeGatherRule;
import com.stormfives.admin.coin.dao.entity.RechargeGatherRuleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeGatherRuleMapper {
    int countByExample(RechargeGatherRuleExample example);

    int deleteByExample(RechargeGatherRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RechargeGatherRule record);

    int insertSelective(RechargeGatherRule record);

    List<RechargeGatherRule> selectByExample(RechargeGatherRuleExample example);

    RechargeGatherRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RechargeGatherRule record, @Param("example") RechargeGatherRuleExample example);

    int updateByExample(@Param("record") RechargeGatherRule record, @Param("example") RechargeGatherRuleExample example);

    int updateByPrimaryKeySelective(RechargeGatherRule record);

    int updateByPrimaryKey(RechargeGatherRule record);
}