package com.flexibleemployment.controller.manage;

import com.flexibleemployment.service.UserService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.UserCheckReqVO;
import com.flexibleemployment.vo.request.UserDeleteReqVO;
import com.flexibleemployment.vo.request.UserPageReqVO;
import com.flexibleemployment.vo.request.UserReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.UserRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/manage/user")
@Api(tags = "用户管理", description = "/manage/user")
@AuthIgnore
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
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody UserReqVO reqVO) {
            return userService.add(reqVO);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody UserReqVO reqVO) {
        return userService.update(reqVO);

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
        return userService.delete(reqVO);
    }



}
