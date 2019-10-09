package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.TaskAttachment;
import com.flexibleemployment.dao.mapper.TaskAttachmentMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.vo.request.TaskAttachmentReqVO;
import com.flexibleemployment.vo.response.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TaskAttachmentService extends BaseService<Long, TaskAttachment, TaskAttachmentMapperExt> {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 查询
     *
     * @return
     */
    @Transactional
    public List<TaskAttachment> queryByTaskId(Long taskId) {
        return mapper.selectByTaskId(taskId);
    }

    /**
     * 新增
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> add(TaskAttachmentReqVO reqVO) {
        TaskAttachment taskAttachment = ConvertUtils.convert(reqVO, TaskAttachment.class);
        taskAttachment.setAttachmentId(snowflakeIdWorker.nextId());
        taskAttachment.setCreatedAt(new Date());
        Integer result = mapper.insertSelective(taskAttachment);
        if (result == 0) {
            return ResultVO.validError("save is failed!");
        }
        return ResultVO.success(result);
    }


    /**
     * 删除
     *
     * @return
     */
    @Transactional
    public ResultVO<Integer> delete(Long taskId) {
        Integer result = mapper.deleteByTaskId(taskId);
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

}
