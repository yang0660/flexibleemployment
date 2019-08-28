package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理列表查询
 */
@Data
@ApiModel(value = "项目管理列表查询")
public class ProjectRespVO {

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目介绍")
    private String projectDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;
}