package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单附件新增参数
 */
@Data
@ApiModel(value = "订单附件新增参数")
public class OrderAttachmentReqVO{
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单附件地址")
    private String attachmentUrl;

}