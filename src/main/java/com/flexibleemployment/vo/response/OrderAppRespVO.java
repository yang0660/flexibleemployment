package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 前端-订单管理列表查询
 */
@Data
@ApiModel(value = "前端-订单管理列表查询")
public class OrderAppRespVO {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "任务ID")
    private Long taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "接单时间")
    private Date createdAt;

    @ApiModelProperty(value = "完成时间")
    private Date updatedAt;

}