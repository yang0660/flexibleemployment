package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 用户管理新增/修改参数
 */
@Data
@ApiModel(value = "用户管理新增/修改参数")
public class UserReqVO {
    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "微信头像")
    private String headerImg;
}