package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目管理-分页查询参数
 */
@Data
@ApiModel(value = "项目管理-分页查询参数")
public class ProjectPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "项目名称")
    private String projectName;
}