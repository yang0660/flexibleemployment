package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.OrderAttachment;

public interface OrderAttachmentMapper extends Mapper<OrderAttachment>{
    int deleteByPrimaryKey(Long attachmentId);

    int insert(OrderAttachment record);

    int insertSelective(OrderAttachment record);

    OrderAttachment selectByPrimaryKey(Long attachmentId);

    int updateByPrimaryKeySelective(OrderAttachment record);

    int updateByPrimaryKey(OrderAttachment record);
}