package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务管理新增/修改参数
 */
@Data
@ApiModel(value = "任务管理新增/修改参数")
public class TaskReqVO {
    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "任务目标")
    private String taskGoal;

    @ApiModelProperty(value = "具体要求")
    private String taskRequest;

    @ApiModelProperty(value = "任务描述")
    private String taskDesc;

    @ApiModelProperty(value = "承接人手机号码")
    private String mobile;

    @ApiModelProperty(value = "任务状态")
    private Byte status;

    @ApiModelProperty(value = "价格")
    private BigDecimal amount;

    @ApiModelProperty(value = "交接时间")
    private Date deliverTime;

    @ApiModelProperty(value = "结算时间")
    private Date settlementTime;
}