package com.stormfives.admin.wallet.dao;

import com.stormfives.admin.wallet.dao.entity.WalletGroupCoinBalance;
import com.stormfives.admin.wallet.dao.entity.WalletGroupCoinBalanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletGroupCoinBalanceMapper {
    int countByExample(WalletGroupCoinBalanceExample example);

    int deleteByExample(WalletGroupCoinBalanceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WalletGroupCoinBalance record);

    int insertSelective(WalletGroupCoinBalance record);

    List<WalletGroupCoinBalance> selectByExample(WalletGroupCoinBalanceExample example);

    WalletGroupCoinBalance selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WalletGroupCoinBalance record, @Param("example") WalletGroupCoinBalanceExample example);

    int updateByExample(@Param("record") WalletGroupCoinBalance record, @Param("example") WalletGroupCoinBalanceExample example);

    int updateByPrimaryKeySelective(WalletGroupCoinBalance record);

    int updateByPrimaryKey(WalletGroupCoinBalance record);
}