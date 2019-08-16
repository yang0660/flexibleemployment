package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会员管理列表查询
 */
@Data
@ApiModel(value = "会员管理列表查询")
public class WhiteListRespVO {
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "状态")
    private Byte status;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;


}