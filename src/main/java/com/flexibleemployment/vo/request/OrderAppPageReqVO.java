package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端-订单管理-分页查询参数
 */
@Data
@ApiModel(value = "前端-订单管理-分页查询参数")
public class OrderAppPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "任务状态:1-进行中; 2-待结算(仅后台)； 3-已完成")
    private Byte status;


}