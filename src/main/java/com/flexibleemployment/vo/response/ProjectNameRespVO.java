package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 前端任务页面模糊查询列表
 */
@Data
@ApiModel(value = "前端任务页面模糊查询列表")
public class ProjectNameRespVO {
    @ApiModelProperty(value = "项目名称")
    private String projectName;
}