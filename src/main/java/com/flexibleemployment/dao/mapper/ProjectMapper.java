package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.Project;

public interface ProjectMapper extends Mapper<Project> {
    int deleteByPrimaryKey(Long projectId);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Long projectId);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);
}