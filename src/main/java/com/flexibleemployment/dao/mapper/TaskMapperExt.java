package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.vo.request.TaskAppPageReqVO;
import com.flexibleemployment.vo.request.TaskPageReqVO;
import com.flexibleemployment.vo.response.TaskAppRespVO;
import com.flexibleemployment.vo.response.TaskRespVO;

import java.util.List;

public interface TaskMapperExt extends TaskMapper{

    long selectCount(TaskPageReqVO reqVO);

    List<TaskRespVO> selectList(TaskPageReqVO reqVO);

    long selectCountApp(TaskAppPageReqVO reqVO);

    List<TaskAppRespVO> selectListApp(TaskAppPageReqVO reqVO);

    Integer delete(Long taskId);

    TaskRespVO selectByTaskId(Long taskId);

    TaskRespVO selectByProjectName(String projectName);


}