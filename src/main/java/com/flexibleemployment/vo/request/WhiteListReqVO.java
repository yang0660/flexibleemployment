package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员管理新增/修改参数
 */
@Data
@ApiModel(value = "会员管理新增/修改参数")
public class WhiteListReqVO {
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "状态")
    private Byte status;
}