package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户token
 */
@Data
@ApiModel(value = "用户token")
public class UserTokenRespVO {
    @ApiModelProperty(value = "openId")
    private String openId;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "微信头像")
    private String headerImg;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "sessionKey")
    private String sessionKey;

    @ApiModelProperty(value = "是否绑定手机号: false未绑定，true已经绑定")
    private boolean bindMobile;

    @ApiModelProperty(value = "是否是会员")
    private boolean member;

}