package com.stormfives.admin.wallet.dao;

import com.stormfives.admin.wallet.controller.req.WalletGroupReq;
import com.stormfives.admin.wallet.controller.response.WalletGroupVo;
import com.stormfives.admin.wallet.dao.entity.WalletGroup;
import com.stormfives.admin.wallet.dao.entity.WalletGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WalletGroupMapper {
    int countByExample(WalletGroupExample example);

    int deleteByExample(WalletGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WalletGroup record);

    int insertSelective(WalletGroup record);

    List<WalletGroup> selectByExample(WalletGroupExample example);

    WalletGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WalletGroup record, @Param("example") WalletGroupExample example);

    int updateByExample(@Param("record") WalletGroup record, @Param("example") WalletGroupExample example);

    int updateByPrimaryKeySelective(WalletGroup record);

    int updateByPrimaryKey(WalletGroup record);

    List<WalletGroupVo> selectListByFilter(WalletGroupReq walletGroupReq);
}