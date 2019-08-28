package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户详情查询参数
 */
@Data
@ApiModel(value = "用户详情查询参数")
public class UserDetailReqVO {
    @ApiModelProperty(value = "openId")
    private String openId;

}