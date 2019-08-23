package com.flexibleemployment.controller;

import com.flexibleemployment.service.TaskService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.TaskAppTaskIdReqVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.TaskRespVO;
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
@RequestMapping("/app/task")
@Api(tags = "前端-任务管理", description = "/app/task")
@AuthIgnore
public class TaskController {

    @Autowired
    TaskService taskService;

    /**
     * 任务查询-任务ID
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryByTaskId")
    @ApiOperation("任务查询-任务ID")
    public ResultVO<TaskRespVO> queryByTaskId(@RequestBody TaskAppTaskIdReqVO reqVO) {
        return taskService.queryByTaskId(reqVO);
    }
}
