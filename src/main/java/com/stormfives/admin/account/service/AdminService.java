package com.stormfives.admin.account.service;

import com.stormfives.admin.account.dao.AdminMapper;
import com.stormfives.admin.account.dao.entity.Admin;
import com.stormfives.admin.account.dao.entity.AdminExample;
import com.stormfives.admin.common.exception.InvalidArgumentException;
import com.stormfives.admin.token.domain.Token;
import com.stormfives.admin.token.service.OauthService;
import com.stormfives.admin.token.vo.TokenVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private OauthService oauthService;

    protected Logger logger = LoggerFactory.getLogger(AdminService.class);

    public static String INIT_PWD="123456";


    public TokenVo login(String name, String password) throws InvalidArgumentException {

        if (StringUtils.isBlank(name))
            throw new InvalidArgumentException(" ");
        Admin admin = getAdminByName(name.trim());

        if (admin == null)
            throw new InvalidArgumentException(" ");

        // 验证账户密码是否正确
        boolean access = BCrypt.checkpw(password, admin.getPassword());
        if (access){
            Token token = oauthService.generateToken(admin.getId(), "admin");

            if (token ==null){
                logger.error(" param name:{},password:{}", name,password);
            }
            TokenVo tokenVo = new TokenVo();
            BeanUtils.copyProperties(token,tokenVo);

            return tokenVo;
        }else {
            throw new InvalidArgumentException(" ");
        }


    }

    public Admin getAdminByName(String name) {

        AdminExample adminExample = new AdminExample();
        adminExample.or().andNameEqualTo(name);
        adminExample.setOrderByClause("id desc");
        List<Admin> adminList = adminMapper.selectByExample(adminExample);

        if (adminList!=null && adminList.size()>0)
            return adminList.get(0);

        return null;

    }

    public boolean addAdmin(String name, String password, String phone, String realName ,Integer adminId) throws InvalidArgumentException {

        if (StringUtils.isBlank(name))
            throw new InvalidArgumentException(" !");

        if (StringUtils.isBlank(phone))
            throw new InvalidArgumentException(" !");

        Admin admin = getAdminByName(name);

        if (admin != null)
            throw new InvalidArgumentException(" !");

        admin = new Admin();

        admin.setName(name);
        admin.setPhone(phone);
        admin.setRealName(realName);
        admin.setCreatedBy(adminId);
        admin.setUpdatedBy(adminId);

        if (StringUtils.isBlank(password)){

            password = BCrypt.hashpw(INIT_PWD, BCrypt.gensalt());
        }else{
            password = BCrypt.hashpw(password, BCrypt.gensalt());
        }

        admin.setPassword(password);

        int insert = adminMapper.insertSelective(admin);


        return (insert>0)?true:false;
    }

    public boolean resetPassword(Integer adminId, String password,String name) throws InvalidArgumentException {


        if (StringUtils.isBlank(name))
            throw new InvalidArgumentException("用户名不能为空!");

        Admin admin = getAdminById(adminId);
        if (admin == null)
            throw new InvalidArgumentException("账号不存在!");

        admin.setName(name);

        admin.setUpdatedBy(adminId);

        if (StringUtils.isBlank(password)){
            throw new InvalidArgumentException("新密码不能为空!");

        }else{
            password = BCrypt.hashpw(password, BCrypt.gensalt());
        }

        admin.setPassword(password);

        int insert = adminMapper.updateByPrimaryKeySelective(admin);


        return (insert>0)?true:false;

    }

    public Admin getAdminById(Integer adminId) {

        AdminExample adminExample = new AdminExample();
        adminExample.or().andIdEqualTo(adminId);

        List<Admin> adminList = adminMapper.selectByExample(adminExample);

        if (adminList != null && adminList.size()>0)
            return adminList.get(0);

        return null;
    }
}
