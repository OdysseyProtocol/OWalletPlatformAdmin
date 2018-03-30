package com.stormfives.admin.wallet.controller.response;

import com.stormfives.admin.wallet.dao.entity.WalletGroup;
import com.stormfives.admin.wallet.dao.entity.WalletGroupDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/19
 * Time: 下午2:47
 */
public class WalletGroupVo extends WalletGroup {

    private List<WalletGroupDetailVo> list = new ArrayList<>();

    private int walletCount; //钱包组地址数量

    private double balanceCount; //钱包组总余额

    private String platforms; //钱包组所属平台

    public List<WalletGroupDetailVo> getList() {
        return list;
    }

    public void setList(List<WalletGroupDetailVo> list) {
        this.list = list;
    }

    public int getWalletCount() {
        return walletCount;
    }

    public void setWalletCount(int walletCount) {
        this.walletCount = walletCount;
    }

    public double getBalanceCount() {
        return balanceCount;
    }

    public void setBalanceCount(double balanceCount) {
        this.balanceCount = balanceCount;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }
}
