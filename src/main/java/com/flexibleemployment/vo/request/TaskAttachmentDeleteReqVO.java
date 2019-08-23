package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务附件删除参数
 */
@Data
@ApiModel(value = "任务附件删除参数")
public class TaskAttachmentDeleteReqVO {
    @ApiModelProperty(value = "任务附件ID")
    private Long attachmentId;
}