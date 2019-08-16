package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.Order;

public interface OrderMapper extends Mapper<Order> {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}