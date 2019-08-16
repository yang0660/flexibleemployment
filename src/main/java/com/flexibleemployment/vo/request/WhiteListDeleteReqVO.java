package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会员管理删除参数
 */
@Data
@ApiModel(value = "会员管理删除参数")
public class WhiteListDeleteReqVO {
    @ApiModelProperty(value = "手机号码")
    private String mobile;
}