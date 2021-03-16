package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BaseServiceImpl
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    // 引用mybatis-plus的basemapper
    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public int insert(T t) {

        return baseMapper.insert(t);
    }

    @Override
    public int delete(Integer id) {

        return baseMapper.deleteById(id);
    }

    @Override
    public int update(T t) {
        return baseMapper.updateById(t);
    }

    @Override
    public T selectById(Integer id) {
        return baseMapper.selectById(id);
    }
}
