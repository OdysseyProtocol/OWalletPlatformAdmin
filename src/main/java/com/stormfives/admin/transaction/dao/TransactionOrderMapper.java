package com.stormfives.admin.transaction.dao;

import com.stormfives.admin.transaction.controller.req.TransactionOrderReq;
import com.stormfives.admin.transaction.controller.resp.TransactionOrderVo;
import com.stormfives.admin.transaction.dao.entity.TransactionOrder;
import com.stormfives.admin.transaction.dao.entity.TransactionOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TransactionOrderMapper {
    int countByExample(TransactionOrderExample example);

    int deleteByExample(TransactionOrderExample example);

    int deleteByPrimaryKey(String txHash);

    int insert(TransactionOrder record);

    int insertSelective(TransactionOrder record);

    List<TransactionOrder> selectByExample(TransactionOrderExample example);

    TransactionOrder selectByPrimaryKey(String txHash);

    int updateByExampleSelective(@Param("record") TransactionOrder record, @Param("example") TransactionOrderExample example);

    int updateByExample(@Param("record") TransactionOrder record, @Param("example") TransactionOrderExample example);

    int updateByPrimaryKeySelective(TransactionOrder record);

    int updateByPrimaryKey(TransactionOrder record);

    List<TransactionOrderVo> selectListByFilter(TransactionOrderReq transactionOrderReq);
}