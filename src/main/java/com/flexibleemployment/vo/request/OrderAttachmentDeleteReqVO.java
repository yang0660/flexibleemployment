package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单附件删除参数
 */
@Data
@ApiModel(value = "订单附件删除参数")
public class OrderAttachmentDeleteReqVO {
    @ApiModelProperty(value = "订单附件ID")
    private Long attachmentId;
}