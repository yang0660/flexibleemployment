package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.WhiteList;

public interface WhiteListMapper extends Mapper<WhiteList>{
    int deleteByPrimaryKey(String mobile);

    int insert(WhiteList record);

    int insertSelective(WhiteList record);

    WhiteList selectByPrimaryKey(String mobile);

    int updateByPrimaryKeySelective(WhiteList record);

    int updateByPrimaryKey(WhiteList record);
}