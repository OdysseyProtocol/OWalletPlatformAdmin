package com.stormfives.admin.interceptor;


import com.stormfives.admin.common.exception.UnauthorizedException;
import com.stormfives.admin.common.util.MessageSourceUtil;
import com.stormfives.admin.common.util.WalletUtil;
import com.stormfives.admin.log.dao.LogOperateAdminMapper;
import com.stormfives.admin.log.dao.entity.LogOperateAdmin;
import com.stormfives.admin.token.service.OauthService;
import com.stormfives.admin.token.vo.TokenVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by cc on 16/10/11.
 *
 */
@Component
public class OauthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private OauthService oauthService;

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @Autowired
    LogOperateAdminMapper logOperateAdminMapper;




    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("watch_startTime");



    Logger logger = LoggerFactory.getLogger(OauthInterceptor.class);


    /**
     * This implementation always returns {@code true}.
     *
     * @param request
     * @param response
     * @param handler
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);

        StringBuffer url = request.getRequestURL();

        setLanguage(request);

        if (url.indexOf("/api/isAlive") >=0 || url.indexOf("/token") >=0 || url.indexOf("login") >= 0 | url.indexOf("pic/code") >= 0)
            return super.preHandle(request, response, handler);

        String headToken = request.getHeader("Authorization");

        if (request.getParameter("Authorization") != null){
            headToken = request.getParameter("Authorization");
        }
        if (StringUtils.isBlank(headToken) || headToken.length()<="Bearer ".length())
            throw new UnauthorizedException(messageSourceUtil.getMessage("authorization"));

        String accessToken = headToken.substring("Bearer ".length());
        TokenVo token = oauthService.getTokenByAccessToken(accessToken);

        if (token == null || !token.getScope().equalsIgnoreCase("admin"))
            throw new UnauthorizedException(messageSourceUtil.getMessage("authorization"));

        request.setAttribute("adminId", token.getResourceOwnerId());
        return super.preHandle(request, response, handler);
    }


    public void setLanguage(HttpServletRequest request){
        String language = request.getHeader("Accept-Language");
        if (StringUtils.isNotBlank(language)){
            if (language.indexOf("hant") == 0 || language.indexOf("Hant") > 0){
                LocaleContextHolder.setLocale(new Locale("hant", "CN"));
            }else if(language.indexOf("en-uk") == 0){

                LocaleContextHolder.setLocale(new Locale("en-uk", "GB"));
            }else if(language.indexOf("en-au") == 0){

                LocaleContextHolder.setLocale(new Locale("en-au", "AU"));
            }else if (language.indexOf("en") == 0){

                LocaleContextHolder.setLocale(new Locale("en", "US"));
            }else if (language.indexOf("zh") == 0){

                LocaleContextHolder.setLocale(new Locale("zh", "CN"));
            }else if (language.indexOf("ms") == 0){

                LocaleContextHolder.setLocale(new Locale("ms", "MY"));
            }else if (language.indexOf("th") == 0){

                LocaleContextHolder.setLocale(new Locale("th", "TH"));
            }else if(language.indexOf("nl") == 0){

                LocaleContextHolder.setLocale(new Locale("nl", "NL"));
            }else if(language.indexOf("ko") == 0){

                LocaleContextHolder.setLocale(new Locale("ko", "KR"));
            }else if(language.indexOf("fr-ch") == 0){

                LocaleContextHolder.setLocale(new Locale("fr-ch", "CH"));
            }else if(language.indexOf("fr") == 0){

                LocaleContextHolder.setLocale(new Locale("fr", "FR"));
            }else if(language.indexOf("de-ch") == 0){

                LocaleContextHolder.setLocale(new Locale("de-ch", "CH"));
            }else if(language.indexOf("de") == 0){

                LocaleContextHolder.setLocale(new Locale("de", "DE"));
            }
        }else{
            LocaleContextHolder.setLocale(new Locale("en", "US"));
        }
    }



    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        try{
            String actionName = "";
            if(handler instanceof  HandlerMethod){
                HandlerMethod handler2 = (HandlerMethod) handler;
                actionName = handler2.getMethod().getName();
            }else{
                actionName = request.getRequestURI();
            }
            long endTime = System.currentTimeMillis();//
            long beginTime = startTimeThreadLocal.get();//
            int consumeTime = (int)(endTime - beginTime);
            String ip = WalletUtil.getStringIP(request);
            int userId=0;
            if(request.getAttribute("adminId")!=null){
                userId = Integer.valueOf(request.getAttribute("adminId").toString());
            }
            String param ="";
            if(request.getAttribute("param")!=null){
                param =  request.getAttribute("param").toString();
            }
            LogOperateAdmin logOperation = WalletUtil.initCustomerLog(userId,actionName,consumeTime,ip,param);
            logOperateAdminMapper.insertSelective(logOperation);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }


}
