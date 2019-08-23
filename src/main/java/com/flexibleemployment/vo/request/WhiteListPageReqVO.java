package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员管理-分页查询参数
 */
@Data
@ApiModel(value = "会员管理-分页查询参数")
public class WhiteListPageReqVO extends PageRequestVO{
    @ApiModelProperty(value = "姓名/手机号")
    private String arg;
}