package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.Project;
import com.flexibleemployment.dao.mapper.ProjectMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.vo.request.ProjectDeleteReqVO;
import com.flexibleemployment.vo.request.ProjectPageReqVO;
import com.flexibleemployment.vo.request.ProjectReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ProjectListRespVO;
import com.flexibleemployment.vo.response.ProjectRespVO;
import com.flexibleemployment.vo.response.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ProjectService extends BaseService<Long, Project, ProjectMapperExt>{

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    public ResultVO<PageResponseVO<ProjectRespVO>> queryListPage(ProjectPageReqVO reqVO) {
        PageResponseVO<Project> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        PageResponseVO<ProjectRespVO> result = ConvertUtils.convertPage(page, ProjectRespVO.class);
        if (result != null && !CollectionUtils.isEmpty(result.getItems())) {
            return ResultVO.success(result);
        }
        return ResultVO.success(new PageResponseVO<ProjectRespVO>());
    }

    /**
     * 项目下拉框列表
     *
     * @return
     */
    public ResultVO<List<ProjectListRespVO>> queryListAll() {
        List<Project> list = mapper.selectListAll();
        List<ProjectListRespVO> resultList = new ArrayList<ProjectListRespVO>();
        if (!CollectionUtils.isEmpty(list)) {
            resultList = ConvertUtils.convert(list, ProjectListRespVO.class);
        }
        return ResultVO.success(resultList);
    }

    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> add(ProjectReqVO reqVO) {
        Project project = ConvertUtils.convert(reqVO, Project.class);
        project.setProjectId(snowflakeIdWorker.nextId());
        project.setCreatedAt(new Date());
        project.setUpdatedAt(new Date());
        Integer result = mapper.insertSelective(project);
        if (result == 0) {
            return ResultVO.validError("save is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(ProjectReqVO reqVO) {
        Project project = ConvertUtils.convert(reqVO, Project.class);
        project.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(project);
        if (result == 0) {
            return ResultVO.validError("update is failed!");
        }
        return ResultVO.success(result);

    }

    /**
     * 删除
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> delete(ProjectDeleteReqVO reqVO) {
        Integer result = mapper.delete(reqVO.getProjectId());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

}
