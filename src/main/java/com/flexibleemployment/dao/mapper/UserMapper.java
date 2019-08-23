package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.User;

public interface UserMapper extends Mapper<User>{
    int deleteByPrimaryKey(String openId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}