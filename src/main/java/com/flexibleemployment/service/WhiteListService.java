package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.dao.mapper.WhiteListMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.vo.request.WhiteListDeleteReqVO;
import com.flexibleemployment.vo.request.WhiteListPageReqVO;
import com.flexibleemployment.vo.request.WhiteListReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.WhiteListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class WhiteListService extends BaseService<String, WhiteList, WhiteListMapperExt>{

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    public ResultVO<PageResponseVO<WhiteListRespVO>> queryListPage(WhiteListPageReqVO reqVO) {
        PageResponseVO<WhiteList> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        PageResponseVO<WhiteListRespVO> result = ConvertUtils.convertPage(page, WhiteListRespVO.class);
        if (result != null && !CollectionUtils.isEmpty(result.getItems())) {
            return ResultVO.success(result);
        }
        return ResultVO.success(new PageResponseVO<WhiteListRespVO>());
    }


    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public Integer add(WhiteListReqVO reqVO) {
        WhiteList whiteList = ConvertUtils.convert(reqVO, WhiteList.class);
        whiteList.setCreatedAt(new Date());
        whiteList.setUpdatedAt(new Date());
        return mapper.insertSelective(whiteList);

    }

    /**
     * 详情查询
     *
     * @param mobile
     * @return
     */
    @Transactional
    public WhiteList queryByPrimaryKey(String mobile) {
        return mapper.selectByPrimaryKey(mobile);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(WhiteListReqVO reqVO) {
        WhiteList whiteList = ConvertUtils.convert(reqVO, WhiteList.class);
        whiteList.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(whiteList);
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
    public ResultVO<Integer> delete(WhiteListDeleteReqVO reqVO) {
        Integer result = mapper.deleteByPrimaryKey(reqVO.getMobile());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }
}
