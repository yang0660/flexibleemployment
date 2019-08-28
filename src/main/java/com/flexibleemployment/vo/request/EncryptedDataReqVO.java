package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 解密手机号码参数
 */
@Data
@ApiModel(value = "解密手机号码参数")
public class EncryptedDataReqVO {
    @ApiModelProperty(value = "encryptedData")
    private String encryptedData;

    @ApiModelProperty(value = "session_key")
    private String session_key;

    @ApiModelProperty(value = "iv")
    private String iv;

}