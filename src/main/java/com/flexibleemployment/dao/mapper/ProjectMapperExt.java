package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.Project;
import com.flexibleemployment.vo.request.ProjectPageReqVO;

import java.util.List;

public interface ProjectMapperExt extends ProjectMapper {

    long selectCount(ProjectPageReqVO reqVO);

    List<Project> selectList(ProjectPageReqVO reqVO);

    List<Project> selectListAll();

    Integer delete(Long projectId);

}