package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(String unionId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String unionId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}