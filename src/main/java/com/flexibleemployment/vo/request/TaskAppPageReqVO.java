package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端任务页面分页查询参数
 */
@Data
@ApiModel(value = "前端任务页面分页查询参数")
public class TaskAppPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "状态")
    private String status;
}