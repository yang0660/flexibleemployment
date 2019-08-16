package com.flexibleemployment.service;

import com.flexibleemployment.dao.mapper.Mapper;
import com.flexibleemployment.utils.SnowflakeIdWorker;
import com.flexibleemployment.vo.request.PageRequestVO;
import com.flexibleemployment.vo.response.PageResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * 基础Service, 封装了： 单表CRUD、 分页查询、slf4j日志对象、雪花啤酒
 *
 * @author lile
 */
@Slf4j
public class BaseService<K extends Serializable, E, M extends Mapper<E>> {

    public static final long SYSTEM_MANAGER_ID = 1L;

    public static final int MAX_PAGE_SIZE = 100000;

    protected M mapper;

    @Autowired
    protected SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 如果有xxxMapperExt, 子类需重写改方法， 用@Qualifier注解指定
     *
     * @param mapper mapper对象
     */
    @Autowired
    protected void setMapper(M mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public int deleteByPrimaryKey(K pk) {
        return mapper.deleteByPrimaryKey(pk);
    }

    @Transactional
    public int insert(E e) {
        return mapper.insert(e);
    }

    @Transactional
    public int insertSelective(E e) {
        return mapper.insertSelective(e);
    }

    @Transactional
    public int updateByPrimaryKey(E e) {
        return mapper.updateByPrimaryKey(e);
    }

    @Transactional
    public int updateByPrimaryKeySelective(E e) {
        return mapper.updateByPrimaryKeySelective(e);
    }

    @Transactional(readOnly = true)
    public E selectByPrimaryKey(K pk) {
        return mapper.selectByPrimaryKey(pk);
    }

    /**
     * 查询部分(分页)
     *
     * @param condition 条件
     * @return 分页数据
     */
    @Transactional(readOnly = true)
    public List<E> selectList(PageRequestVO condition) {
        return mapper.selectList(condition);
    }

    /**
     * 查询所有, 最大记录数不超过{@link BaseService#MAX_PAGE_SIZE}
     *
     * @param condition 条件， 传子类
     * @return 结果集
     */
    @Transactional(readOnly = true)
    public List<E> selectListMaxSize(@NotNull PageRequestVO condition) {
        condition.setPageNumber(1);
        condition.setPageSize(MAX_PAGE_SIZE);
        return mapper.selectList(condition);
    }

    /**
     * 查询第一条记录
     *
     * @param condition 查询条件， pageSize=1, pageNumber=1
     * @return 实体
     */
    @Transactional(readOnly = true)
    public E selectFirst(@NotNull PageRequestVO condition) {
        condition.setPageNumber(1);
        condition.setPageSize(1);
        List<E> list = selectList(condition);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 查询总记录数
     *
     * @param condition 条件
     * @return 记录数
     */
    @Transactional(readOnly = true)
    public long selectCount(PageRequestVO condition) {
        return mapper.selectCount(condition);
    }

    /**
     * 查询分页数据
     *
     * @param condition 条件
     * @return 分页数据
     */
    @Transactional(readOnly = true)
    public PageResponseVO<E> selectPage(PageRequestVO condition) {
        return selectPage(condition, this::selectCount, this::selectList);
    }

    /**
     * 查询分页数据
     *
     * @param condition   条件
     * @param selectCount 查询记录数
     * @param selectList  查询结果集
     * @param <C>         分页查询条件， 要求继承自PageRequestDTO
     * @param <VO>        DTO或者实体， 实现序列化接口就行
     * @return 分页数据
     */
    @Transactional(readOnly = true)
    protected <C extends PageRequestVO, VO> PageResponseVO<VO> selectPage(C condition, Function<C, Long> selectCount, Function<C, List<VO>> selectList) {
        PageResponseVO<VO> pageResponseDTO = new PageResponseVO<>();

        long count = selectCount.apply(condition);
        pageResponseDTO.measureTotalPage((int) count, condition.getPageSize());

        if (count > condition.getOffset()) {
            List<VO> items = selectList.apply(condition);
            pageResponseDTO.setItems(items);
        }

        return pageResponseDTO;
    }

}
