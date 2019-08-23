package com.flexibleemployment.controller.manage;

import com.flexibleemployment.service.OrderService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.OrderDeleteReqVO;
import com.flexibleemployment.vo.request.OrderPageReqVO;
import com.flexibleemployment.vo.request.OrderUpdateReqVO;
import com.flexibleemployment.vo.response.OrderRespVO;
import com.flexibleemployment.vo.response.PageResponseVO;
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
@RequestMapping("/manage/order")
@Api(tags = "订单管理", description = "/manage/order")
@AuthIgnore
public class OrderManageController {

    @Autowired
    OrderService orderService;

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListPage")
    @ApiOperation("列表查询-分页")
    public ResultVO<PageResponseVO<OrderRespVO>> queryListPage(@RequestBody OrderPageReqVO reqVO) {
        return orderService.queryListPage(reqVO);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/update")
    @ApiOperation("修改")
    public ResultVO<Integer> update(@RequestBody OrderUpdateReqVO reqVO) {
        return orderService.update(reqVO);
    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/delete")
    @ApiOperation("删除")
    public ResultVO<Integer> delete(@RequestBody OrderDeleteReqVO reqVO) {
        return orderService.delete(reqVO);
    }
}
