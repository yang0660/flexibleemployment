package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员管理详情查询参数
 */
@Data
@ApiModel(value = "会员管理详情查询参数")
public class WhiteListDetailReqVO {
    @ApiModelProperty(value = "手机号码")
    private String mobile;
}