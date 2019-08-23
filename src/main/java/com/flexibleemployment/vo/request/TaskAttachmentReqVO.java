package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务附件新增参数
 */
@Data
@ApiModel(value = "任务附件新增参数")
public class TaskAttachmentReqVO {
    @ApiModelProperty(value = "任务ID")
    private Long taskId;

    @ApiModelProperty(value = "任务附件地址")
    private String attachmentUrl;

}