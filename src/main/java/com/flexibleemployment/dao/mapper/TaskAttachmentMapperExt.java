package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.TaskAttachment;

import java.util.List;

public interface TaskAttachmentMapperExt extends TaskAttachmentMapper{
    Integer delete(Long attachmentId);

    Integer deleteByTaskId(Long taskId);

    List<TaskAttachment> selectByTaskId(Long taskId);
}