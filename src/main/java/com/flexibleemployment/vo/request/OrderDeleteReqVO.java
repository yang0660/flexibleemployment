package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单删除参数
 */
@Data
@ApiModel(value = "订单删除参数")
public class OrderDeleteReqVO {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

}