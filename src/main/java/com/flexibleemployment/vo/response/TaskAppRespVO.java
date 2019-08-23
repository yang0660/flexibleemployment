package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端任务页面列表查询
 */
@Data
@ApiModel(value = "前端任务页面列表查询")
public class TaskAppRespVO {
    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
}