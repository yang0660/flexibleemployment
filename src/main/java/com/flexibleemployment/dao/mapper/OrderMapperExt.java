package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.Order;
import com.flexibleemployment.vo.request.OrderAmountReqVO;
import com.flexibleemployment.vo.request.OrderAppPageReqVO;
import com.flexibleemployment.vo.request.OrderPageReqVO;

import java.math.BigDecimal;
import java.util.List;

public interface OrderMapperExt extends OrderMapper{

    long selectCount(OrderPageReqVO reqVO);

    List<Order> selectList(OrderPageReqVO reqVO);

    long selectCountApp(OrderAppPageReqVO reqVO);

    List<Order> selectListApp(OrderAppPageReqVO reqVO);

    BigDecimal selectSumAmount(OrderAmountReqVO reqVO);

    Integer delete(Long orderId);
}