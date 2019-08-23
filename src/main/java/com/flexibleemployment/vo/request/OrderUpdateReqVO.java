package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单管理更新参数
 */
@Data
@ApiModel(value = "订单管理更新参数")
public class OrderUpdateReqVO {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "任务状态:1-进行中; 2-待结算(仅后台)； 3-已完成")
    private Byte status;
}