package com.flexibleemployment.controller.manage;

import com.flexibleemployment.service.ProjectService;
import com.flexibleemployment.service.TaskService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.utils.file.ExcelUtils;
import com.flexibleemployment.vo.request.TaskDeleteReqVO;
import com.flexibleemployment.vo.request.TaskPageReqVO;
import com.flexibleemployment.vo.request.TaskReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.TaskRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/manage/task")
@Api(tags = "任务管理", description = "/manage/task")
@AuthIgnore
public class TaskManageController {

    @Autowired
    TaskService taskService;
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
    public ResultVO<PageResponseVO<TaskRespVO>> queryListPage(@RequestBody TaskPageReqVO reqVO) {
        return taskService.queryListPage(reqVO);
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody TaskReqVO reqVO) {
        return ResultVO.success(taskService.add(reqVO));
    }

    /**
     * 导入excel新增任务
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/addByImportExcel")
    @ApiOperation("导入excel新增任务")
    public ResultVO<String> addByImportExcel(@RequestParam("projectId") Long projectId, @RequestParam("file") MultipartFile file) throws IOException, ParseException {
        List<List<String>> tasks = ExcelUtils.readRows(file.getInputStream());
        TaskReqVO reqVO = new TaskReqVO();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        //i=1,表示从第二行开始取值，第一行是字段名
        for (int i = 1; i < tasks.size(); i++) {
            int j = 0;
            //取到第i行的数据，逐个赋值给VO
            reqVO.setTaskName(tasks.get(i).get(j++));
            reqVO.setTaskGoal(tasks.get(i).get(j++));
            reqVO.setTaskDesc(tasks.get(i).get(j++));
            reqVO.setMobile(tasks.get(i).get(j++));
            reqVO.setDeliverTime(dateFormat.parse(tasks.get(i).get(j++)));
            reqVO.setSettlementTime(dateFormat.parse(tasks.get(i).get(j)));
            reqVO.setProjectId(projectId);
            //赋值好的VO插入数据库
            Integer result = taskService.add(reqVO);
            if (result != 1) {
                return ResultVO.validError("Insert row " + i + " failed!");
            }
        }
        return ResultVO.success("success");
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody TaskReqVO reqVO) {
        return taskService.update(reqVO);
    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody TaskDeleteReqVO reqVO) {
        return taskService.delete(reqVO);
    }

}
