package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端-订单管理-查询已收服务费参数
 */
@Data
@ApiModel(value = "前端-订单管理-查询已收服务费参数")
public class OrderAmountReqVO{
    @ApiModelProperty(value = "openId")
    private String openId;
}