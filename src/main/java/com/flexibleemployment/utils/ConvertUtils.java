package com.flexibleemployment.utils;

import com.flexibleemployment.vo.response.PageResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Entity->DTO转换工具类
 *
 * @author lile
 */
@Slf4j
public abstract class ConvertUtils {

    /**
     * 分页Entity转VO
     *
     * @param pageResponseDTO 分页DTO
     * @param dtoClass        dto类型
     * @param <E>             Entity
     * @param <V>             VO
     * @return PageResponseVO<V>
     */
    public static <E, V> PageResponseVO<V> convertPage(PageResponseVO<E> pageResponseDTO, Class<V> dtoClass) {
        PageResponseVO<V> response = new PageResponseVO<>();
        copyProperties(pageResponseDTO, response);

        response.setItems(convert(pageResponseDTO.getItems(), dtoClass));
        return response;
    }

    /**
     * 转换对象列表
     *
     * @param source      源对象数组
     * @param targetClass 目标类型
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return 目标对象列表
     */
    public static <S, T> List<T> convert(List<S> source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        if (source.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return source.stream().map(s -> convert(s, targetClass)).collect(Collectors.toList());
    }


    public static <S, T> List<T> convert(List<S> source, Class<T> targetClass, BiConsumer<S, T> biConsumer) {
        if (source == null) {
            return null;
        }
        if (source.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        List<T> targetList = new ArrayList<>();
        for (S s : source) {
            T t = convert(s, targetClass);
            biConsumer.accept(s, t);
            targetList.add(t);
        }
        return targetList;
    }

    /**
     * 转换单个对象
     *
     * @param source      源对象
     * @param targetClass 目标类型
     * @param <S>         源类型
     * @param <T>         目标类型
     * @return 目标对象
     */
    public static <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target = newInstance(targetClass);

        return copyProperties(source, target);
    }

    /**
     * 复制属性， 采用Spring BeanUtils
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <S>    源类型
     * @param <T>    目标类型
     * @return 目标对象
     */
    public static <S, T> T copyProperties(S source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 如果t为空， 则返回空对象。 否则返回t
     *
     * @param t    对象
     * @param type 对象类型
     * @param <T>  对象类型
     * @return t或type.newInstance
     */
    public static <T> T null2Empty(T t, Class<T> type) {
        return t == null ? newInstance(type) : t;
    }

    /**
     * 根据Class信息创建实例
     *
     * @param type Class类型信息
     * @param <T>  类型
     * @return 实例
     * @throws RuntimeException 构造器调用失败
     */
    public static <T> T newInstance(Class<T> type) {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeWriteMethod(Object obj, String propertyName, Object... args) {
        Class<?> clazz = obj.getClass();
        try {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, propertyName);
            if (propertyDescriptor == null) {
                return null;
            }
            Method writeMethod = propertyDescriptor.getWriteMethod();
            return writeMethod.invoke(obj, args);
        } catch (Exception e) {
            log.error("调用" + propertyName + "的set方法异常", e);
            throw new BizException("调用%s.%s的set方法异常", clazz.getName(), propertyName);
        }
    }

    public static Object invokeReadMethod(Object obj, String propertyName) {
        Class<?> clazz = obj.getClass();
        try {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, propertyName);
            if (propertyDescriptor == null) {
                return null;
            }
            Method readMethod = propertyDescriptor.getReadMethod();
            return readMethod.invoke(obj);
        } catch (Exception e) {
            log.error("调用" + propertyName + "的get方法异常", e);
            throw new BizException("调用%s.%s的get方法异常", clazz.getName(), propertyName);
        }
    }

    public static boolean existsField(Class<?> clazz, String propertyName) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, propertyName);
        return propertyDescriptor != null;
    }
}
