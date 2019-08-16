package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.TaskAttachment;

public interface TaskAttachmentMapper {
    int deleteByPrimaryKey(Long attachmentId);

    int insert(TaskAttachment record);

    int insertSelective(TaskAttachment record);

    TaskAttachment selectByPrimaryKey(Long attachmentId);

    int updateByPrimaryKeySelective(TaskAttachment record);

    int updateByPrimaryKey(TaskAttachment record);
}