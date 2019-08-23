package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户管理删除参数
 */
@Data
@ApiModel(value = "用户管理删除参数")
public class UserDeleteReqVO{
    @ApiModelProperty(value = "openId")
    private String openId;
}