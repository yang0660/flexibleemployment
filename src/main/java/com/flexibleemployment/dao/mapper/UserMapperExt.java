package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.vo.request.UserPageReqVO;

import java.util.List;

public interface UserMapperExt extends UserMapper{

    long selectCount(UserPageReqVO reqVO);

    List<User> selectList(UserPageReqVO reqVO);

    User selectByMobile(String mobile);
}