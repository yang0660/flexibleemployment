package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目管理新增/修改参数
 */
@Data
@ApiModel(value = "项目管理新增/修改参数")
public class ProjectReqVO {
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目介绍")
    private String projectDesc;
}