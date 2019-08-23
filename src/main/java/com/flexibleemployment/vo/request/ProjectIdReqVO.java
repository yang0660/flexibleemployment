package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端查询项目关联任务列表参数
 */
@Data
@ApiModel(value = "前端查询项目关联任务列表参数")
public class ProjectIdReqVO {
    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "任务状态")
    private String status;
}