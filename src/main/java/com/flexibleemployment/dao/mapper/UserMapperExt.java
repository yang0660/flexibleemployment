package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.vo.request.UserPageReqVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapperExt extends UserMapper{

    long selectCount(UserPageReqVO reqVO);

    List<User> selectList(UserPageReqVO reqVO);

    User checkWhiteList(String mobile);
}