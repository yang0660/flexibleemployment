package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端任务页面模糊查询参数
 */
@Data
@ApiModel(value = "前端任务页面模糊查询参数")
public class ProjectNameReqVO {
    @ApiModelProperty(value = "项目名称")
    private String projectName;
}