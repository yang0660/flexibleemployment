package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.UserAccount;

public interface UserAccountMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserAccount record);

    int insertSelective(UserAccount record);

    UserAccount selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserAccount record);

    int updateByPrimaryKey(UserAccount record);
}