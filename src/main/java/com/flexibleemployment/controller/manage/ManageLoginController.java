package com.flexibleemployment.controller.manage;

import com.flexibleemployment.dao.entity.UserAccount;
import com.flexibleemployment.enums.DeviceTypeEnum;
import com.flexibleemployment.enums.PassWordStatusEnum;
import com.flexibleemployment.helper.ShiroHelper;
import com.flexibleemployment.service.UserAccountService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.shiro.ManageUserNamePasswordToken;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.PrincipalContext;
import com.flexibleemployment.utils.RequestUtil;
import com.flexibleemployment.vo.request.ManageLoginReqVO;
import com.flexibleemployment.vo.response.LoginRespVO;
import com.flexibleemployment.vo.response.ResultVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/manage")
@Api(tags = "后台登录", description = "/manage")
public class ManageLoginController {

    @Autowired
    private UserAccountService userAccountService;

    /**
     * login
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/login")
    @AuthIgnore
    public ResultVO<LoginRespVO> login(@RequestBody ManageLoginReqVO reqVO, HttpServletRequest request, HttpServletResponse response) {
        String requestIp = RequestUtil.getUserRealIP(request); //请求的真实IP
        // 参数校验
        UserAccount user = userAccountService.queryByUserName(reqVO.getUserName());
        ResultVO result = PrincipalContext.checkManageUser(user);

        if (result == null) {
            Subject subject = SecurityUtils.getSubject();
            try {
                ManageUserNamePasswordToken usernamePasswordDeviceToken = new ManageUserNamePasswordToken(String.valueOf(user.getUserId()), reqVO.getPassword(), DeviceTypeEnum.H5);
                subject.login(usernamePasswordDeviceToken);

                //登录成功
                LoginRespVO loginRespVO = ConvertUtils.convert(user, LoginRespVO.class);
                //登录token
                String sessionId = (String) subject.getSession().getId();
                loginRespVO.setToken(sessionId);
                Session session = SecurityUtils.getSubject().getSession(false);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("passwordStatus", PassWordStatusEnum.EFFECTIVE.name());
                result = ResultVO.success("登录成功");
                result.setData(loginRespVO);
            } catch (UnknownAccountException e) {
                log.error("用户不存在!", e);
                result = ResultVO.validError("用户不存在!");
            } catch (AuthenticationException e) {
                log.error("用户名或密码错误!", e);
                result = ResultVO.validError("用户名或密码错误!");
            } catch (Exception e) {
                log.error("网络异常，请稍后重试!", e);
                result = ResultVO.validError("网络异常，请稍后重试!");
            }
        }

        return result;
    }

    @PostMapping(value = "/logout")
    public ResultVO logout(HttpServletRequest request, HttpServletResponse response) {
        String requestIp = RequestUtil.getUserRealIP(request);
        log.info("退出登录,IP:", requestIp);
        Long userId = PrincipalContext.getCurrentUserId();
        String userName = PrincipalContext.getCurrentUserName();
        // 获取当前的Subject
        Subject curUser = ShiroHelper.getSubject(request, response);
        curUser.logout();
        log.info(userName,",退出登录!");
        return ResultVO.success("登出成功");
    }
}
