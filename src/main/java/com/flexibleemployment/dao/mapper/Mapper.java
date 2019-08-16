package com.flexibleemployment.dao.mapper;

import com.flexibleemployment.vo.request.PageRequestVO;

import java.io.Serializable;
import java.util.List;

/**
 * 公用Mapper接口
 *
 * @param <E> 实体类型
 */
public interface Mapper<E> {
    /**
     * 按主键删除
     *
     * @param primaryKey 主键
     * @return 删除行数
     */
    int deleteByPrimaryKey(Serializable primaryKey);

    /**
     * 插入记录
     *
     * @param e 实体数据
     * @return 插入行数
     */
    int insert(E e);

    /**
     * 选择插入记录
     *
     * @param e 实体数据
     * @return 插入行数
     */
    int insertSelective(E e);

    /**
     * 根据主键更新
     *
     * @param e 实体
     * @return 更新条数
     */
    int updateByPrimaryKey(E e);

    /***
     * 根据主键更新, 按需更新
     * @param e
     * @return
     */
    int updateByPrimaryKeySelective(E e);

    /**
     * 根据主键查询
     *
     * @param primaryKey 主键
     * @return 实体
     */
    E selectByPrimaryKey(Serializable primaryKey);

    /**
     * 根据条件查询分页记录
     *
     * @param condition 查询条件
     * @return 记录集
     */
    List<E> selectList(PageRequestVO condition);

    /**
     * 查过记录数
     *
     * @param condition 查询条件
     * @return 记录数
     */
    long selectCount(PageRequestVO condition);
}
