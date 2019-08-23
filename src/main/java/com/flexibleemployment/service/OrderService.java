package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.Order;
import com.flexibleemployment.dao.entity.OrderAttachment;
import com.flexibleemployment.dao.entity.Task;
import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.dao.mapper.OrderMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.vo.request.*;
import com.flexibleemployment.vo.response.OrderAppRespVO;
import com.flexibleemployment.vo.response.OrderRespVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderService extends BaseService<Long, Order, OrderMapperExt> {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    OrderAttachmentService orderAttachmentService;

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    /**
     * 列表查询-分页
     *
     * @return
     */
    public ResultVO<PageResponseVO<OrderRespVO>> queryListPage(OrderPageReqVO reqVO) {
        PageResponseVO<Order> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        PageResponseVO<OrderRespVO> result = ConvertUtils.convertPage(page, OrderRespVO.class);
        if (result != null && !CollectionUtils.isEmpty(result.getItems())) {
            return ResultVO.success(result);
        }
        return ResultVO.success(new PageResponseVO<OrderRespVO>());
    }

    /**
     * 前端-列表查询-分页
     *
     * @return
     */
    public ResultVO<PageResponseVO<OrderAppRespVO>> queryListAppPage(OrderAppPageReqVO reqVO) {
        PageResponseVO<Order> page = selectPage(reqVO, mapper::selectCountApp, mapper::selectListApp);
        PageResponseVO<OrderAppRespVO> result = ConvertUtils.convertPage(page, OrderAppRespVO.class);
        if (result != null && !CollectionUtils.isEmpty(result.getItems())) {
            return ResultVO.success(result);
        }
        return ResultVO.success(new PageResponseVO<OrderAppRespVO>());
    }

    /**
     * 已收服务费-订单总金额
     *
     * @return
     */
    public ResultVO<BigDecimal> querySumAmount(OrderAmountReqVO reqVO) {
        return ResultVO.success(mapper.selectSumAmount(reqVO));
    }


    /**
     * 新增
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> add(OrderReqVO reqVO) {
        User user = userService.checkWhiteList(reqVO.getMobile());
        if (user.getIsWhiteList()!=1) {
            return ResultVO.validError("not a valid whiteList member");
        }

        Order order = new Order();
        order.setTaskId(reqVO.getTaskId());
        order.setOrderId(snowflakeIdWorker.nextId());
        order.setOpenId(user.getOpenId());
        order.setStatus((byte) 1);
//            order.setStatus(Byte.valueOf("1"));
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        Integer result = mapper.insertSelective(order);
        if (result == 0) {
            return ResultVO.validError("save is failed!");
        }
        //已接单后，设置对应任务状态为2-已接单
        Task task = new Task();
        task.setTaskId(order.getTaskId());
        task.setStatus((byte)2);
        taskService.updateByPrimaryKeySelective(task);
        return ResultVO.success(result);
    }

    /**
     * 更新
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(OrderUpdateReqVO reqVO) {
        if (reqVO.getStatus() == 3) {
            return ResultVO.validError("order was closed");
        }
        Order order = ConvertUtils.convert(reqVO, Order.class);
        order.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(order);
        if (result == 0) {
            return ResultVO.validError("update is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 删除
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> delete(OrderDeleteReqVO reqVO) {
        //根据orderId查询附件表，并删除所有关联附件（逻辑删除）
        List<OrderAttachment> list = orderAttachmentService.queryByOrderId(reqVO.getOrderId());
        list.forEach(orderAttachment -> orderAttachmentService.delete(orderAttachment.getAttachmentId()));
        Integer result = mapper.delete(reqVO.getOrderId());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

}
