package com.flexibleemployment.controller;

import com.flexibleemployment.service.OrderService;
import com.flexibleemployment.shiro.AuthIgnore;
import com.flexibleemployment.vo.request.OrderAmountReqVO;
import com.flexibleemployment.vo.request.OrderAppPageReqVO;
import com.flexibleemployment.vo.request.OrderReqVO;
import com.flexibleemployment.vo.response.OrderAppRespVO;
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

import java.math.BigDecimal;

@RestController
@Slf4j
@RequestMapping("/app/order")
@Api(tags = "前端-订单管理", description = "/app/order")
@AuthIgnore
public class OrderController {

    @Autowired
    OrderService orderService;

    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation("新增")
    public ResultVO<Integer> add(@RequestBody OrderReqVO reqVO) {
            return orderService.add(reqVO);
    }

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    @PostMapping(value = "/queryListAppPage")
    @ApiOperation("列表查询-分页")
    public ResultVO<PageResponseVO<OrderAppRespVO>> queryListAppPage(@RequestBody OrderAppPageReqVO reqVO) {
        return orderService.queryListAppPage(reqVO);
    }


    /**
     * 已收服务费-订单总金额
     *
     * @param
     * @return
     */
    @PostMapping(value = "/querySumAmount")
    @ApiOperation("已收服务费-订单总金额")
    public ResultVO<BigDecimal> querySumAmount(@RequestBody OrderAmountReqVO reqVO) {
        return orderService.querySumAmount(reqVO);
    }



}
