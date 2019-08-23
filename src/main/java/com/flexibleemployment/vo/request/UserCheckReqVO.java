package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查是否白名单成员参数
 */
@Data
@ApiModel(value = "检查是否白名单成员参数")
public class UserCheckReqVO {
    @ApiModelProperty(value = "mobile")
    private String mobile;
}