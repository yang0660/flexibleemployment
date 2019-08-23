package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.Order;
import com.flexibleemployment.dao.entity.OrderAttachment;
import com.flexibleemployment.dao.mapper.OrderAttachmentMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.vo.request.OrderAttachmentReqVO;
import com.flexibleemployment.vo.response.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderAttachmentService extends BaseService<Long, OrderAttachment, OrderAttachmentMapperExt> {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    OrderService orderService;

    /**
     * 查询
     *
     * @return
     */
    @Transactional
    public List<OrderAttachment> queryByOrderId(Long orderId) {
        return mapper.selectByOrderId(orderId);
    }

    /**
     * 新增
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> add(OrderAttachmentReqVO reqVO) {
        OrderAttachment orderAttachment = ConvertUtils.convert(reqVO, OrderAttachment.class);
        orderAttachment.setAttachmentId(snowflakeIdWorker.nextId());
        orderAttachment.setCreatedAt(new Date());
        Integer result = mapper.insertSelective(orderAttachment);
        if (result == 0) {
            return ResultVO.validError("save is failed!");
        }
        //上传订单附件，相应订单的状态更改为2-待结算
        Order order = new Order();
        order.setOrderId(orderAttachment.getOrderId());
        order.setStatus((byte)2);
        orderService.updateByPrimaryKeySelective(order);
        return ResultVO.success(result);
    }


    /**
     * 删除
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> delete(Long attachmentId) {
        Integer result = mapper.delete(attachmentId);
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

}
