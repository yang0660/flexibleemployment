package com.flexibleemployment.shiro;

import com.flexibleemployment.service.shiro.ManagerUserDetailService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.util.Set;

/**
 * 后台管理系统权限控制
 * Created by zhuhecheng 2019-01-08
 */
public class ManageRealm extends AuthorizingRealm implements ApplicationListener<ApplicationReadyEvent> {

    private static final String REALM_NAME = ManageRealm.class.getName();

    @Autowired
    private ManagerUserDetailService managerUserDetailService;

    @Override
    public void setName(String name) {
        super.setName(REALM_NAME);
    }

    @Override
    protected Object getAuthorizationCacheKey(PrincipalCollection principals) {
        UserAuthPrincipal userAuthPrincipal = (UserAuthPrincipal) principals.getPrimaryPrincipal();
        return userAuthPrincipal.getPrincipal();
    }

    @Override
    protected Object getAuthenticationCacheKey(AuthenticationToken token) {
        ManageUserNamePasswordToken tk = (ManageUserNamePasswordToken) token;
        return tk.getUsername();
    }

    /**
     * 授权
     *
     * @param principals 当前用户身份信息
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserAuthPrincipal principal = (UserAuthPrincipal) principals.getPrimaryPrincipal();
        Long userId = Long.valueOf(principal.getPrincipal());
        Set<String> permissions = managerUserDetailService.queryPermissionsByUserId(userId);
        boolean existPermission = permissions != null && !permissions.isEmpty();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (existPermission) {
            authorizationInfo.addStringPermissions(permissions);
        }
        Set<String> roles = managerUserDetailService.queryRolesByUserId(userId);
        boolean existRole = roles != null && !roles.isEmpty();
        if (existRole) {
            authorizationInfo.addRoles(roles);
        }

        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param token 认证token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        ManageUserNamePasswordToken tk = (ManageUserNamePasswordToken) token;
        SimpleAuthenticationInfo authenticationInfo = null;
        String userIdStr = String.valueOf(tk.getPrincipal());
        UserAuthPrincipal userAuthPrincipal = managerUserDetailService.loadPrincipalByUserId(Long.valueOf(userIdStr));
        if (userAuthPrincipal != null) {
            userAuthPrincipal.setDeviceType(tk.getDeviceType());
            userAuthPrincipal.setReserve("manage");
            authenticationInfo = new SimpleAuthenticationInfo(userAuthPrincipal, userAuthPrincipal.getCredentials(), REALM_NAME);
            authenticationInfo.setCredentialsSalt(new CustomByteSource(userAuthPrincipal.getSalt()));
        }
        return authenticationInfo;
    }

    @Override
    public Class getAuthenticationTokenClass() {
        return ManageUserNamePasswordToken.class;
    }

    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

    }
}
