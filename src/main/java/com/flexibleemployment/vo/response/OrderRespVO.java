package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 订单管理列表查询
 */
@Data
@ApiModel(value = "订单管理列表查询")
public class OrderRespVO {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "任务ID")
    private Long taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "承接人")
    private String userName;

    @ApiModelProperty(value = "状态")
    private Byte status;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "价格")
    private BigDecimal amount;

    @ApiModelProperty(value = "交接时间")
    private Date deliverTime;

    @ApiModelProperty(value = "接单时间")
    private Date createdAt;

}