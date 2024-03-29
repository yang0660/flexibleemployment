package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.dao.entity.UserAccount;
import org.apache.ibatis.annotations.Param;

public interface UserAccountMapperExt extends UserAccountMapper {

    UserAccount selectByUserName(@Param("userName") String userName);
}
