package com.lll.online.exam.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseMapper<T> {

    int deleteById(Integer id);

    int insert(T record);

    T selectById(Integer id);

    int updateById(T record);
}
