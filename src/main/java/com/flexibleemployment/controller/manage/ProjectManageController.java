package com.flexibleemployment.controller.manage;

import com.flexibleemployment.service.ProjectService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.ProjectDeleteReqVO;
import com.flexibleemployment.vo.request.ProjectPageReqVO;
import com.flexibleemployment.vo.request.ProjectReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ProjectListRespVO;
import com.flexibleemployment.vo.response.ProjectRespVO;
import com.flexibleemployment.vo.response.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/manage/project")
@Api(tags = "项目管理", description = "/manage/project")
@AuthIgnore
public class ProjectManageController {

    @Autowired
    ProjectService projectService;

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListPage")
    @ApiOperation("列表查询-分页")
    public ResultVO<PageResponseVO<ProjectRespVO>> queryListPage(@RequestBody ProjectPageReqVO reqVO) {
        return projectService.queryListPage(reqVO);
    }

    /**
     * 下拉框列表
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListAll")
    @ApiOperation("下拉框列表")
    public ResultVO<List<ProjectListRespVO>> queryListAll() {
        return projectService.queryListAll();
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody ProjectReqVO reqVO) {
            return projectService.add(reqVO);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody ProjectReqVO reqVO) {
        return projectService.update(reqVO);

    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody ProjectDeleteReqVO reqVO) {
        return projectService.delete(reqVO);
    }

}
