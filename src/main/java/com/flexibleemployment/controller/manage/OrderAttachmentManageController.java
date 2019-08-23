package com.flexibleemployment.controller.manage;

import com.flexibleemployment.service.OrderAttachmentService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.OrderAttachmentDeleteReqVO;
import com.flexibleemployment.vo.request.OrderAttachmentReqVO;
import com.flexibleemployment.vo.response.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/manage/orderAttachment")
@Api(tags = "订单附件管理", description = "/manage/orderAttachment")
@AuthIgnore
public class OrderAttachmentManageController {

    @Autowired
    OrderAttachmentService orderAttachmentService;


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody OrderAttachmentReqVO reqVO) {
        return orderAttachmentService.add(reqVO);
    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody OrderAttachmentDeleteReqVO reqVO) {
        return orderAttachmentService.delete(reqVO.getAttachmentId());
    }

}
