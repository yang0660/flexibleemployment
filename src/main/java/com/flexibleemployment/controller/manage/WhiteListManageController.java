package com.flexibleemployment.controller.manage;

import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.service.WhiteListService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.WhiteListDeleteReqVO;
import com.flexibleemployment.vo.request.WhiteListDetailReqVO;
import com.flexibleemployment.vo.request.WhiteListPageReqVO;
import com.flexibleemployment.vo.request.WhiteListReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.WhiteListRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/manage/whitelist")
@Api(tags = "会员管理", description = "/manage/whitelist")
@AuthIgnore
public class WhiteListManageController {

    @Autowired
    WhiteListService whiteListService;

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListPage")
    @ApiOperation("列表查询-分页")
    public ResultVO<PageResponseVO<WhiteListRespVO>> queryListPage(@RequestBody WhiteListPageReqVO reqVO) {
        return whiteListService.queryListPage(reqVO);
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody WhiteListReqVO reqVO) {
        return ResultVO.success(whiteListService.add(reqVO));
    }

    /**
     * 导入excel新增白名单
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/addByImportExcel")
    @ApiOperation("导入excel新增白名单")
    public ResultVO<String> addByImportExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return whiteListService.addByImportExcel(file);
    }


    /**
     * 详情查询
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/queryByPrimaryKey")
    @ApiOperation("详情查询")
    public ResultVO<WhiteList> queryByPrimaryKey(@RequestBody WhiteListDetailReqVO reqVO) {
        return ResultVO.success(whiteListService.queryByPrimaryKey(reqVO.getMobile()));
    }


    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody WhiteListReqVO reqVO) {
        return whiteListService.update(reqVO);

    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody WhiteListDeleteReqVO reqVO) {
        return whiteListService.delete(reqVO);
    }

}
