package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端任务页面详情查询参数
 */
@Data
@ApiModel(value = "前端任务页面详情查询参数")
public class TaskAppReqVO{
    @ApiModelProperty(value = "任务ID")
    private Long taskId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;
}