package com.flexibleemployment.controller.manage;

import com.flexibleemployment.service.TaskAttachmentService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.TaskAttachmentDeleteReqVO;
import com.flexibleemployment.vo.request.TaskAttachmentReqVO;
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
@RequestMapping("/manage/taskAttachment")
@Api(tags = "任务附件管理", description = "/manage/taskAttachment")
@AuthIgnore
public class TaskAttachmentManageController {

    @Autowired
    TaskAttachmentService taskAttachmentService;


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody TaskAttachmentReqVO reqVO) {
        return taskAttachmentService.add(reqVO);
    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody TaskAttachmentDeleteReqVO reqVO) {
        return taskAttachmentService.delete(reqVO.getTaskId());
    }

}
