package com.flexibleemployment.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequestVO implements Serializable {
    @ApiModelProperty(value = "当前页码：第几页", example = "1")
    public int pageNumber = 1;

    @ApiModelProperty(value = "每页记录条数", example = "20")
    public int pageSize = 20;

    public int getOffset() {
        return (this.pageNumber - 1) * this.pageSize;
    }
}
