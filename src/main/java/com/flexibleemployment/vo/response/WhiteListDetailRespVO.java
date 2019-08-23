package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 详情查询-模糊查询
 */
@Data
@ApiModel(value = "详情查询-模糊查询")
public class WhiteListDetailRespVO {
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    private String userName;
}