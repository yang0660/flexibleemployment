package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录token请求
 * 
 * @author
 * @date
 */
@Data
@ApiModel(value = "登录参数")
public class LoginTokenRequsetDTOExt {

	@ApiModelProperty("微信授权code")
	private String wxcode;

}