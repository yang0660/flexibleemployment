package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目管理删除参数
 */
@Data
@ApiModel(value = "项目管理删除参数")
public class ProjectDeleteReqVO {
    @ApiModelProperty(value = "项目ID")
    private Long projectId;
}