package com.flexibleemployment.utils;

import com.flexibleemployment.dao.entity.UserAccount;
import com.flexibleemployment.shiro.UserAuthPrincipal;
import com.flexibleemployment.vo.response.ResultVO;
import com.google.common.base.Preconditions;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by zhangwanli on 2017/11/9.
 */
public class PrincipalContext {

    public static UserAuthPrincipal getUserPrincipal() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        //Preconditions.checkNotNull(principal, "user must be authenticated before call this method.");
        return (UserAuthPrincipal) principal;
    }

    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static Long getCurrentUserId() {
        UserAuthPrincipal userPrincipal = getUserPrincipal();
        return null == userPrincipal ? null : Long.valueOf(userPrincipal.getPrincipal());
    }

    /**
     * 获取当前登录用户名
     *
     * @return
     */
    public static String getCurrentUserName() {
        UserAuthPrincipal userPrincipal = getUserPrincipal();
        return null == userPrincipal ? null : userPrincipal.getUserName();
    }

    public static String getSessionId() {
        Serializable sessionId = SecurityUtils.getSubject().getSession(false).getId();
        Preconditions.checkNotNull(sessionId, "user must be authenticated before call this method.");
        return sessionId.toString();
    }

    public static ResultVO checkManageUser(UserAccount userAccount) {
        ResultVO result = null;
        if (userAccount == null) {
            return ResultVO.validError("该用户不存在!");
        }

        return result;
    }

    public static ResultVO checkAppUser(UserAccount userAccount) {
        ResultVO result = null;
        if (userAccount == null) {
            return ResultVO.validError("该用户不存在!");
        }
        if (StringUtils.isEmpty(userAccount.getPassword())) {
            result = ResultVO.validError("用户未注册!");
            result.setRespCode(ResultVO.UNREGISTER_ERROR_CODE);
            return result;
        }

        return result;
    }

    public static ResultVO checkAppWxUser(UserAccount userAccount) {
        ResultVO result = null;
        if (userAccount == null) {
            result = ResultVO.validError("请先绑定帐号!");
            result.setRespCode(ResultVO.UNBIND_ERROR_CODE); //未绑定APP帐号
            return result;
        }
        if (StringUtils.isEmpty(userAccount.getPassword())) {
            return ResultVO.validError("用户未注册!");
        }

        return result;
    }

    /**
     * 用户密码加密
     *
     * @param password
     * @param salt
     * @return
     */
    public static String getMd5HashPwd(String password, String salt) {
        return new Md5Hash(password, salt, 3).toString(); //迭代次数为3
    }

}
