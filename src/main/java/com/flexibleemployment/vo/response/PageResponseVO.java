package com.flexibleemployment.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponseVO<T> implements Serializable {
    @ApiModelProperty("总记录数")
    private Integer totalCount = 0;

    @ApiModelProperty("总页数")
    private Integer totalPage = 0;

    public void measureTotalPage(int totalCount, int pageSize) {
        this.totalCount = totalCount;
        if (totalCount < 1) {
            return;
        }
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
    }


    @ApiModelProperty("数据")
    private List<T> items = new ArrayList<>();

    /*public void measureTotalPage(int totalCount, int pageSize) {
        this.totalCount = totalCount;
        if (totalCount < 1) {
            return;
        }
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }*/
}
