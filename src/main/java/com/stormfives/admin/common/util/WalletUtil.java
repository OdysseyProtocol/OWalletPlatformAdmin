package com.stormfives.admin.common.util;

import com.stormfives.admin.log.dao.entity.LogOperateAdmin;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class WalletUtil {

    public static LogOperateAdmin initCustomerLog(int userId, String actionName, int consumeTime, String ip,String param) {
        LogOperateAdmin logOperation = new LogOperateAdmin();
        logOperation.setActpath(actionName);
        logOperation.setConsumetime(consumeTime);
        logOperation.setIp(ip);
        logOperation.setCreatetime(new Date());
        logOperation.setUserid(userId);
        logOperation.setParam(param);
        return logOperation;
    }

    public static String getStringIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip)) {
            ip = ip.split(",")[0];
        }
        return ip;
    }




}
