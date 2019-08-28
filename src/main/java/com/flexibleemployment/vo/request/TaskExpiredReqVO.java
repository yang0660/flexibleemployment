package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询任务是否已过期参数
 */
@Data
@ApiModel(value = "查询任务是否已过期参数")
public class TaskExpiredReqVO {
    @ApiModelProperty(value = "任务ID")
    private Long taskId;
}