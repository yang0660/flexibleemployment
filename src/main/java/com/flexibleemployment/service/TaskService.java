package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.Task;
import com.flexibleemployment.dao.entity.TaskAttachment;
import com.flexibleemployment.dao.mapper.TaskMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.vo.request.*;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.TaskRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.internal.LongArrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskService extends BaseService<Long, Task, TaskMapperExt>{

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private OrderService orderService;

    @Autowired
    TaskAttachmentService taskAttachmentService;

    /**
     * 列表查询-分页
     *
     * @return
     */
    public ResultVO<PageResponseVO<TaskRespVO>> queryListPage(TaskPageReqVO reqVO) {
        PageResponseVO<TaskRespVO> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        if (page != null && !CollectionUtils.isEmpty(page.getItems())) {
            return ResultVO.success(page);
        }
        return ResultVO.success(new PageResponseVO<TaskRespVO>());
    }

    /**
     * 前端任务详情查询-任务ID
     *
     * @return
     */
    @Transactional
    public ResultVO<TaskRespVO> queryByTaskId(TaskAppTaskIdReqVO reqVO) {
        TaskRespVO respVO = mapper.selectByTaskId(reqVO.getTaskId());
        if (respVO == null) {
            return ResultVO.success(new TaskRespVO());
        }
        return ResultVO.success(respVO);
    }

    /**
     * 新增
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> add(TaskReqVO reqVO) {
            Task task = ConvertUtils.convert(reqVO, Task.class);
            task.setTaskId(snowflakeIdWorker.nextId());
            task.setCreatedAt(new Date());
            task.setUpdatedAt(new Date());

            String mobile = reqVO.getMobile();
            if (mobile!=null&& mobile!=""){
                task.setStatus((byte)2);
                OrderReqVO orderReqVO = new OrderReqVO();
                orderReqVO.setTaskId(Long.valueOf(reqVO.getTaskId()));
                orderReqVO.setMobile(mobile);
                orderService.add(orderReqVO);
            }
            Integer result = mapper.insertSelective(task);
            if (result == 0) {
                return ResultVO.validError("save is failed!");
            }
            return ResultVO.success(result);
    }

    /**
     * 更新
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(@RequestBody TaskReqVO reqVO) {
        if (reqVO.getStatus() != 1) {
            return ResultVO.validError("task is been received, can not be updated");
        }
        Task task = ConvertUtils.convert(reqVO, Task.class);
        task.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(task);
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
    public ResultVO<Integer> delete(@RequestBody TaskDeleteReqVO reqVO) {
        if (reqVO.getStatus() != 1) {
            return ResultVO.validError("task is been received, can not be deleted");
        }
        //根据taskId查询附件表，并删除所有关联附件（逻辑删除）
        List<TaskAttachment> list = taskAttachmentService.queryByTaskId(reqVO.getTaskId());
        list.forEach(taskAttachment ->taskAttachmentService.delete(taskAttachment.getAttachmentId()));
        Integer result = mapper.delete(reqVO.getTaskId());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

}
