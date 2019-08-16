package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.Task;

public interface TaskMapper extends Mapper<Task>{
    int deleteByPrimaryKey(Long taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(Long taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}