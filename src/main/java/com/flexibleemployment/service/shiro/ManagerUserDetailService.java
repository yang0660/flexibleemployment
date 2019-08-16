package com.flexibleemployment.service.shiro;

import com.alibaba.fastjson.JSON;
import com.flexibleemployment.dao.entity.UserAccount;
import com.flexibleemployment.dao.mapper.UserAccountMapperExt;
import com.flexibleemployment.shiro.UserAuthPrincipal;
import com.flexibleemployment.shiro.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Created by zhangwanli on 2017/10/12.
 */
@Slf4j
@Service
public class ManagerUserDetailService implements UserDetailService {

    @Autowired
    private UserAccountMapperExt userAccountMapperExt;

    /**
     * 认证时调用，查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserAuthPrincipal loadPrincipalByUserId(Long userId) {
        UserAuthPrincipal userAuthPrincipal = new UserAuthPrincipal();
        UserAccount user = userAccountMapperExt.selectByPrimaryKey(userId);
        if (user != null) {
            //用户未注册
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new DisabledAccountException();
                //状态正常
            } else {
                userAuthPrincipal.setPrincipal(String.valueOf(user.getUserId()));
                userAuthPrincipal.setCredentials(user.getPassword());
                userAuthPrincipal.setSalt(user.getSalt());
                userAuthPrincipal.setUserName(user.getUserName());
                userAuthPrincipal.setReserve("");          //业务保留域，传递手机号
            }
        } else {
            //用户不存在
            throw new UnknownAccountException();
        }
        log.info("登录成功===》" + JSON.toJSONString(userAuthPrincipal));
        return userAuthPrincipal;
    }

    @Override
    public UserAuthPrincipal loadPrincipalByOpenId(String openId, Byte platformId) {
        return null;
    }


    /**
     * 授权时调用，查询用户角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> queryRolesByUserId(Long userId) {
        return null;
    }


    /**
     * 授权时调用，查询用户资源列表
     *
     * @param userId
     * @return
     */
    @Override
    public Set<String> queryPermissionsByUserId(Long userId) {
        return null;
    }

}
