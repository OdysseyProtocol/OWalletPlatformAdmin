package com.stormfives.admin.log.dao;

import com.stormfives.admin.log.dao.entity.LogOperateAdmin;
import com.stormfives.admin.log.dao.entity.LogOperateAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogOperateAdminMapper {
    int countByExample(LogOperateAdminExample example);

    int deleteByExample(LogOperateAdminExample example);

    int deleteByPrimaryKey(Integer logid);

    int insert(LogOperateAdmin record);

    int insertSelective(LogOperateAdmin record);

    List<LogOperateAdmin> selectByExample(LogOperateAdminExample example);

    LogOperateAdmin selectByPrimaryKey(Integer logid);

    int updateByExampleSelective(@Param("record") LogOperateAdmin record, @Param("example") LogOperateAdminExample example);

    int updateByExample(@Param("record") LogOperateAdmin record, @Param("example") LogOperateAdminExample example);

    int updateByPrimaryKeySelective(LogOperateAdmin record);

    int updateByPrimaryKey(LogOperateAdmin record);
}