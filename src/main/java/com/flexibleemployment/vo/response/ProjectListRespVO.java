package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目下拉框列表
 */
@Data
@ApiModel(value = "项目下拉框列表")
public class ProjectListRespVO {

    @ApiModelProperty(value = "项目ID")
    private Long projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;
}