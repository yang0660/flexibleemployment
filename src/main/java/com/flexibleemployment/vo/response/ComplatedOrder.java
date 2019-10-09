package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("已完成订单")
public class ComplatedOrder implements Serializable {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;
    @ApiModelProperty(value = "任务ID")
    private Long taskId;
    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "订单创建时间")
    private Date createdAt;
    @ApiModelProperty(value = "任务名称")
    private String taskName;
    @ApiModelProperty(value = "项目名称")
    private String projectDesc;
}
