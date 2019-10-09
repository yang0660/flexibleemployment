package com.flexibleemployment.controller;

import com.flexibleemployment.service.ProjectService;
import com.flexibleemployment.service.TaskService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.ProjectPageReqVO;
import com.flexibleemployment.vo.request.TaskAppReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ProjectRespVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.TaskNameRespVO;
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
@RequestMapping("/app/project")
@Api(tags = "前端-项目管理", description = "/app/project")
@AuthIgnore
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    TaskService taskService;

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListPage")
    @ApiOperation("列表查询-分页")
    @AuthIgnore
    public ResultVO<PageResponseVO<ProjectRespVO>> queryListPage(@RequestBody ProjectPageReqVO reqVO) {
        ResultVO<PageResponseVO<ProjectRespVO>> resultVO = projectService.queryListPage(reqVO);

        for (ProjectRespVO projectRespVO : resultVO.getData().getItems()){
            //查询任务列表
            TaskAppReqVO taskAppReqVO = new TaskAppReqVO();
            taskAppReqVO.setProjectId(projectRespVO.getProjectId());
            //taskAppReqVO.setStatus("1");

            ResultVO<List<TaskNameRespVO>> resultVO1=taskService.queryByProjectId0(taskAppReqVO);
            projectRespVO.setTask(resultVO1.getData());
        }

        return  resultVO;
    }


}
