package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务管理-分页查询参数
 */
@Data
@ApiModel(value = "任务管理-分页查询参数")
public class TaskPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "任务名称")
    private String taskName;
}