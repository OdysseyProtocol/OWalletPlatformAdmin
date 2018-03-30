package com.stormfives.admin.wallet.dao;

import com.stormfives.admin.wallet.dao.entity.WalletGroupDetail;
import com.stormfives.admin.wallet.dao.entity.WalletGroupDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletGroupDetailMapper {
    int countByExample(WalletGroupDetailExample example);

    int deleteByExample(WalletGroupDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WalletGroupDetail record);

    int insertSelective(WalletGroupDetail record);

    List<WalletGroupDetail> selectByExample(WalletGroupDetailExample example);

    WalletGroupDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WalletGroupDetail record, @Param("example") WalletGroupDetailExample example);

    int updateByExample(@Param("record") WalletGroupDetail record, @Param("example") WalletGroupDetailExample example);

    int updateByPrimaryKeySelective(WalletGroupDetail record);

    int updateByPrimaryKey(WalletGroupDetail record);
}