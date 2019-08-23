package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.OrderAttachment;

import java.util.List;

public interface OrderAttachmentMapperExt extends OrderAttachmentMapper{

    Integer delete(Long attachmentId);

    List<OrderAttachment> selectByOrderId(Long orderId);
}