package com.flexibleemployment.service.shiro;

import com.alibaba.fastjson.JSON;
import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.service.UserService;
import com.flexibleemployment.shiro.UserAuthPrincipal;
import com.flexibleemployment.shiro.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by zhangwanli on 2017/10/12.
 */
@Slf4j
@Service
public class ManagerUserDetailService implements UserDetailService {

    @Autowired
    UserService userService;

    @Override
    public UserAuthPrincipal loadPrincipalByUserId(Long userId) {
        return null;
    }

    /**
     * 认证时调用，查询用户信息
     *
     * @return
     */
    @Override
    public UserAuthPrincipal loadPrincipalByOpenId(String openId) {
        UserAuthPrincipal userAuthPrincipal = new UserAuthPrincipal();
        User user = userService.selectByPrimaryKey(openId);
        if (user != null) {
            userAuthPrincipal.setPrincipal(String.valueOf(user.getOpenId()));
            userAuthPrincipal.setReserve("");          //业务保留域，传递手机号
        } else {
            //用户不存在
            throw new UnknownAccountException();
        }
        log.info("登录===》" + JSON.toJSONString(userAuthPrincipal));
        return userAuthPrincipal;
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
