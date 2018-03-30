package com.stormfives.admin.wallet.controller;

import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.common.response.FailResponse;
import com.stormfives.admin.common.response.ResponseValue;
import com.stormfives.admin.common.response.SuccessResponse;
import com.stormfives.admin.common.util.MessageSourceUtil;
import com.stormfives.admin.token.domain.Page;
import com.stormfives.admin.wallet.controller.req.MerchantInfoReq;
import com.stormfives.admin.wallet.controller.req.WalletGroupDetailReq;
import com.stormfives.admin.wallet.controller.req.WalletGroupReq;
import com.stormfives.admin.wallet.dao.entity.MerchantInfo;
import com.stormfives.admin.wallet.dao.entity.WalletGroup;
import com.stormfives.admin.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lyc
 * Date: 2018/3/15
 * Time: 下午8:39
 */
@RestController
@RequestMapping("/api/coin/")
public class WalletController {


    @Autowired
    private WalletService walletService;

    private Logger logger = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private MessageSourceUtil messageSourceUtil;


    /**
     *
     * @param request
     * @param merchantInfoReq
     * @return
     */
    @GetMapping("v1/merchant-info/list")
    public Page getMerchantInfoList(HttpServletRequest request, @ModelAttribute MerchantInfoReq merchantInfoReq) {


        return walletService.getMerchantInfoList(merchantInfoReq);
    }

    /**
     *
     * @param request
     * @param merchantInfoReq
     * @return
     */
    @GetMapping("v1/merchant-info/list-no-page")
    public List<MerchantInfo> getMerchantInfoListNoPage(HttpServletRequest request, @ModelAttribute MerchantInfoReq merchantInfoReq) {


        return walletService.getMerchantInfoListNopage();
    }

    /**
     *
     * @param request
     * @param merchantInfoReq
     * @return
     */
    @GetMapping("v1/wallet-group/all")
    public List<WalletGroup> getWalletListALl(HttpServletRequest request, @ModelAttribute MerchantInfoReq merchantInfoReq) {


        return walletService.getWalletListAll();
    }


    /**
     *
     * @param request
     * @param merchantInfo
     * @return
     */
    @PostMapping("v1/merchant-info")
    public ResponseValue addMerchantInfo(HttpServletRequest request, @RequestBody MerchantInfo merchantInfo) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.addMerchantInfo(merchantInfo, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));
        }

        return new SuccessResponse(messageSourceUtil.getMessage("success"));
    }


    /**
     */
    @PostMapping("v1/merchant-info/wallet-group-add")
    public ResponseValue merchantAddWalletGroup(HttpServletRequest request, @RequestBody WalletGroupReq walletGroupReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.merchantAddWalletGroup(walletGroupReq, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));
        }

        return new SuccessResponse(messageSourceUtil.getMessage("success"));
    }

    /**
     *
     * @param request
     * @param merchantInfo
     * @return
     */
    @PostMapping("v1/merchant-info/del")
    public ResponseValue deleteMerchantInfo(HttpServletRequest request, @RequestBody MerchantInfoReq merchantInfo) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.deleteMerchantInfo(merchantInfo, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));
        }

        return new SuccessResponse(messageSourceUtil.getMessage("success"));
    }


    /**
     *
     * @param request
     * @param merchantInfoReq
     * @return
     */
    @PutMapping("v1/merchant-info")
    public ResponseValue editMerchantInfo(HttpServletRequest request, @RequestBody MerchantInfoReq merchantInfoReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.editMerchantInfo(merchantInfoReq, adminId);
        } catch (InvalidArgumentException e) {

            return new FailResponse(3001, e.getMessage());

        } catch (IllegalAccessException e) {
            return new FailResponse(3002, messageSourceUtil.getMessage("false"));
        } catch (InvocationTargetException e) {
            return new FailResponse(3003, messageSourceUtil.getMessage("false"));

        } catch (Exception e) {
            return new FailResponse(3004, messageSourceUtil.getMessage("false"));
        }

        return new SuccessResponse(messageSourceUtil.getMessage("success"));

    }

    /**
     *
     * @param request
     * @param walletGroupReq
     * @return
     */
    @GetMapping("v1/wallet-group/exclusions")
    public List<WalletGroup> getOtherWalletGroupList(HttpServletRequest request,
                                                     @ModelAttribute WalletGroupReq walletGroupReq)
            throws InvocationTargetException, IllegalAccessException {


        return walletService.getOtherWalletGroupList(walletGroupReq);
    }


    /**
     */
    @GetMapping("v1/merchant-info/wallet-group")
    public List<WalletGroup> getWalletGroupByMerchantId(@RequestParam("id") Integer id) {
        return walletService.getWalletGroupByMerchantId(id);
    }


    /**
     *
     * @param request
     * @param walletGroupReq
     * @return
     */
    @GetMapping("v1/wallet-group")
    public Page getWalletGroupList(HttpServletRequest request, @ModelAttribute WalletGroupReq walletGroupReq) throws InvocationTargetException, IllegalAccessException {


        return walletService.getWalletGroupList(walletGroupReq);
    }


    /**
     *
     * @param request
     * @param walletGroupReq
     * @return
     */
    @PostMapping("v1/wallet-group")
    public ResponseValue addWalletGroup(HttpServletRequest request, @RequestBody WalletGroupReq walletGroupReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.addWalletGroup(walletGroupReq, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));

        }
        return new SuccessResponse("message", messageSourceUtil.getMessage("success"));
    }


    /**
     */
    @PutMapping("v1/wallet-group")
    public ResponseValue editWalletGroup(HttpServletRequest request, @RequestBody WalletGroupReq walletGroupReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.editWalletGroup(walletGroupReq, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));

        }
        return new SuccessResponse("message", messageSourceUtil.getMessage("success"));
    }

    @PostMapping("v1/wallet-group/del")
    public ResponseValue deleteWalletGroup(HttpServletRequest request, @RequestBody WalletGroupReq walletGroupReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        try {
            boolean success = walletService.deleteWalletGroup(walletGroupReq, adminId);

        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));

        }
        return new SuccessResponse("message", messageSourceUtil.getMessage("success"));
    }


    /**
     */

    @PostMapping("v1/wallet-group/detail/remove")
    public ResponseValue removeGroupDetail(HttpServletRequest request, @RequestBody WalletGroupReq walletGroupReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");
        boolean success = false;
        try {
            success = walletService.removeGroupDetail(walletGroupReq, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));
        }
        return success ? new SuccessResponse("message", messageSourceUtil.getMessage("success")) : new FailResponse(messageSourceUtil.getMessage("false"));
    }


    /**
     */

    @PutMapping("v1/wallet-group/detail/add")
    public ResponseValue addGroupDetail(HttpServletRequest request, @RequestBody WalletGroupDetailReq walletGroupReq) {
        Integer adminId = (Integer) request.getAttribute("adminId");

        boolean success = false;

        try {
              success = walletService.addGroupDetail(walletGroupReq, adminId);
        } catch (InvalidArgumentException e) {
            return new FailResponse(e.getMessage());
        } catch (Exception e) {
            return new FailResponse(messageSourceUtil.getMessage("false"));

        }
        return success ? new SuccessResponse("message", messageSourceUtil.getMessage("success")) : new FailResponse(messageSourceUtil.getMessage("false"));

    }


}
