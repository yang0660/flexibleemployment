package com.flexibleemployment.service;

import com.flexibleemployment.dao.entity.User;
import com.flexibleemployment.dao.mapper.UserMapperExt;
import com.flexibleemployment.utils.ConvertUtils;
import com.flexibleemployment.vo.request.UserDeleteReqVO;
import com.flexibleemployment.vo.request.UserPageReqVO;
import com.flexibleemployment.vo.request.UserReqVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import com.flexibleemployment.vo.response.ResultVO;
import com.flexibleemployment.vo.response.UserRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class UserService extends BaseService<String, User, UserMapperExt>{

    /**
     * 列表查询-分页
     *
     * @param
     * @return
     */
    public ResultVO<PageResponseVO<UserRespVO>> queryListPage(UserPageReqVO reqVO) {
        PageResponseVO<User> page = selectPage(reqVO, mapper::selectCount, mapper::selectList);
        PageResponseVO<UserRespVO> result = ConvertUtils.convertPage(page, UserRespVO.class);
        if (result != null && !CollectionUtils.isEmpty(result.getItems())) {
            return ResultVO.success(result);
        }
        return ResultVO.success(new PageResponseVO<UserRespVO>());
    }

    /**
     * 新增
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> add(UserReqVO reqVO) {
        User user = ConvertUtils.convert(reqVO, User.class);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        Integer result = mapper.insertSelective(user);
        if (result == 0) {
            return ResultVO.validError("save is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 修改
     *
     * @param reqVO
     * @return
     */
    @Transactional
    public ResultVO<Integer> update(UserReqVO reqVO) {
        User user = ConvertUtils.convert(reqVO, User.class);
        user.setUpdatedAt(new Date());
        Integer result = mapper.updateByPrimaryKeySelective(user);
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
    public ResultVO<Integer> delete(UserDeleteReqVO reqVO) {
        Integer result = mapper.deleteByPrimaryKey(reqVO.getOpenId());
        if (result == 0) {
            return ResultVO.validError("delete is failed!");
        }
        return ResultVO.success(result);
    }

    /**
     * 检查是不是白名单成员
     *
     * @param mobile
     * @return
     */
    public User checkWhiteList(String mobile) {
        return mapper.checkWhiteList(mobile);
    }
}
