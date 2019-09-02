package com.flexibleemployment.shiro;

import java.util.Set;

/**
 * Created by zhuhecheng 2019-01-08
 */
public interface UserDetailService {

    UserAuthPrincipal loadPrincipalByUserId(Long userId);

    UserAuthPrincipal loadPrincipalByOpenId(String openId);

    Set<String> queryRolesByUserId(Long userId);

    Set<String> queryPermissionsByUserId(Long userId);

}
