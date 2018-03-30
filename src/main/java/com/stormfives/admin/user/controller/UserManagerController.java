package com.stormfives.admin.user.controller;

import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.token.domain.Page;
import com.stormfives.admin.user.controller.req.CoinBalanceReq;
import com.stormfives.admin.user.controller.req.UserWalletInfoReq;
import com.stormfives.admin.user.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by y on 2018/3/14.
 */


@RestController
@RequestMapping("/api/coin/")
public class UserManagerController {
    @Autowired
    private UserManagerService userManagerService;

    /**
     * 1003: usercoinbalance
     * localhost:9006/api/coinbalance/v1/users
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "v1/coin-balance/users", method = RequestMethod.GET)
    public Page getUserCoinBalances(@ModelAttribute CoinBalanceReq coinBalanceReq) {
        Page responseValue = userManagerService.selectByPage(coinBalanceReq);
        return responseValue;
    }


    @RequestMapping(value = "v1/coin-balance/v1/coininfo", method = RequestMethod.POST)
    public ResponseValue getCoinInfo() {
        ResponseValue responseValue = userManagerService.getCoinInfo();
        return responseValue;
    }


    @GetMapping("v1/user/walletinfo")
    public Page getUserWalletInfo(@ModelAttribute UserWalletInfoReq userWalletInfo) {
        return userManagerService.getUserWalletInfo(userWalletInfo);
    }


    @GetMapping("v1/user/coin-balance")
    public Page getCoinBalances(@ModelAttribute CoinBalanceReq coinBalanceReq) {
        return userManagerService.getCoinBalances(coinBalanceReq);
    }


}
