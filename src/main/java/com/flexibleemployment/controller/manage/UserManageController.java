package com.flexibleemployment.controller.manage;

import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.service.UserService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.shiro.ManageUserNamePasswordToken;
import com.flexibleemployment.utils.BizException;
import com.flexibleemployment.vo.request.*;
import com.flexibleemployment.vo.response.LoginRespVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.UserRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/manage/user")
@Api(tags = "用户管理", description = "/manage/user")
public class UserManageController {

    @Autowired
    UserService userService;

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListPage")
    @ApiOperation("列表查询-分页")
    public ResultVO<PageResponseVO<UserRespVO>> queryListPage(@RequestBody UserPageReqVO reqVO) {
        return userService.queryListPage(reqVO);
    }

    /**
     * 详情查询
     *
     * @param
     * @return
     */
    @PostMapping(value = "/query")
    @ApiOperation("详情查询")
    public ResultVO<User> query(@RequestBody UserDetailReqVO reqVO) {
        return ResultVO.success(userService.query(reqVO.getOpenId()));
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody UserReqVO reqVO) {
        return ResultVO.success(userService.add(reqVO));
    }

    /**
     * 更新
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("更新")
    public ResultVO<Integer> update(@RequestBody UserReqVO reqVO) {
        return ResultVO.success(userService.update(reqVO));

    }


    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody UserDeleteReqVO reqVO) {
        return ResultVO.success(userService.delete(reqVO));
    }

    @PostMapping(value = "/login")
    @ApiOperation("登陆")
    @AuthIgnore
    public ResultVO<LoginRespVO> login(@RequestBody ManageLoginReqVO reqVO) {
        LoginRespVO loginRespVO = new LoginRespVO();
        Subject subject = SecurityUtils.getSubject();
        ManageUserNamePasswordToken usernamePasswordToken = new ManageUserNamePasswordToken(
                reqVO.getUserName(),
                reqVO.getPassword());
        //进行验证，这里可以捕获异常，然后返回对应信息
        try {
            subject.login(usernamePasswordToken);
            Session session = subject.getSession();
            log.debug(session.getId().toString());
            loginRespVO.setToken(session.getId().toString());
            loginRespVO.setUserName("admin");
        } catch (Exception ex) {
            return ResultVO.validError("账号或密码错误");
        }

        return ResultVO.success(loginRespVO);


    }


}
