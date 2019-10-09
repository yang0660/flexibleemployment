package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单管理新增参数
 */
@Data
@ApiModel(value = "领取任务")
public class AddOrderReqVO {
    @ApiModelProperty(value = "任务ID")
    private Long taskId;

}