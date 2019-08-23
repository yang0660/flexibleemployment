package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单管理-分页查询参数
 */
@Data
@ApiModel(value = "订单管理-分页查询参数")
public class OrderPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "任务名称/订单id")
    private String arg;

}