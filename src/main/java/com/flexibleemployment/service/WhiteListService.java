package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.dao.mapper.WhiteListMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.vo.request.WhiteListPageReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.WhiteListRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WhiteListService  extends BaseService<String, WhiteList, WhiteListMapperExt>{

    /**
     * 列表查询-分页
     *
     * @return
     */
    public PageResponseVO<WhiteListRespVO> queryPageList(WhiteListPageReqVO reqVO) {
        PageResponseVO<WhiteList> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        PageResponseVO<WhiteListRespVO> respVO = ConvertUtils.convertPage(page, WhiteListRespVO.class);
        return respVO;
    }
}
