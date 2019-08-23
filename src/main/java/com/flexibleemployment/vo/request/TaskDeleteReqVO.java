package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务管理删除参数
 */
@Data
@ApiModel(value = "任务管理删除参数")
public class TaskDeleteReqVO {
    @ApiModelProperty(value = "任务ID")
    private Long taskId;

    @ApiModelProperty(value = "任务状态")
    private Byte status;
}