package com.stormfives.admin.coin.dao;

import com.stormfives.admin.coin.dao.entity.ScheduleBlockNum;
import com.stormfives.admin.coin.dao.entity.ScheduleBlockNumExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleBlockNumMapper {
    int countByExample(ScheduleBlockNumExample example);

    int deleteByExample(ScheduleBlockNumExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleBlockNum record);

    int insertSelective(ScheduleBlockNum record);

    List<ScheduleBlockNum> selectByExample(ScheduleBlockNumExample example);

    ScheduleBlockNum selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScheduleBlockNum record, @Param("example") ScheduleBlockNumExample example);

    int updateByExample(@Param("record") ScheduleBlockNum record, @Param("example") ScheduleBlockNumExample example);

    int updateByPrimaryKeySelective(ScheduleBlockNum record);

    int updateByPrimaryKey(ScheduleBlockNum record);
}