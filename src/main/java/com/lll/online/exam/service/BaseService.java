package com.lll.online.exam.service;

import org.apache.ibatis.annotations.Insert;

/**
 * BaseService
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public interface BaseService<T> {
    int insert(T t);
    int delete(Integer id);
    int update(T t);
    T selectById(Integer id);
}
