package com.stormfives.admin.user.dao;

import com.stormfives.admin.user.dao.entity.UserCoinLog;
import com.stormfives.admin.user.dao.entity.UserCoinLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserCoinLogMapper {
    int countByExample(UserCoinLogExample example);

    int deleteByExample(UserCoinLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCoinLog record);

    int insertSelective(UserCoinLog record);

    List<UserCoinLog> selectByExample(UserCoinLogExample example);

    UserCoinLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCoinLog record, @Param("example") UserCoinLogExample example);

    int updateByExample(@Param("record") UserCoinLog record, @Param("example") UserCoinLogExample example);

    int updateByPrimaryKeySelective(UserCoinLog record);

    int updateByPrimaryKey(UserCoinLog record);
}