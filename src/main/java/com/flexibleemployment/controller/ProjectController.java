package com.flexibleemployment.controller;

import com.flexibleemployment.service.ProjectService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.ProjectPageReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
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

@RestController
@Slf4j
@RequestMapping("/app/project")
@Api(tags = "前端-项目管理", description = "/app/project")
@AuthIgnore
public class ProjectController {

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


}
