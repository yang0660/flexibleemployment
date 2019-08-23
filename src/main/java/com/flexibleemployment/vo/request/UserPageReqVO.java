package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理-分页查询参数
 */
@Data
@ApiModel(value = "用户管理-分页查询参数")
public class UserPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "openId")
    private String openId;
}