package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单管理新增参数
 */
@Data
@ApiModel(value = "订单管理新增参数")
public class OrderReqVO{
    @ApiModelProperty(value = "任务ID")
    private Long taskId;

    @ApiModelProperty(value = "手机号码")
    private String mobile;
}