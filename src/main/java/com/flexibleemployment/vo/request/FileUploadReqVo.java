package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Data
@ApiModel(value = "附件上传")
public class FileUploadReqVo {
    @ApiModelProperty("数据id。可以为taskid/ordeid")
    private Long id;

    private CommonsMultipartFile file;
}
