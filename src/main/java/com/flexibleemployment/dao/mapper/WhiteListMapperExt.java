package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.dao.entity.WhiteList;
import com.flexibleemployment.vo.request.WhiteListPageReqVO;

import java.util.List;

public interface WhiteListMapperExt extends WhiteListMapper{

    long selectCount(WhiteListPageReqVO reqVO);

    List<WhiteList> selectList(WhiteListPageReqVO reqVO);

}