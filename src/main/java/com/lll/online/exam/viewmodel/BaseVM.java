package com.lll.online.exam.viewmodel;

import com.lll.online.exam.utility.ModelMapperUtil;
import org.modelmapper.ModelMapper;

/**
 * 返回类基础类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
public class BaseVM {
    protected static ModelMapper modelMapper = ModelMapperUtil.instance();

    public static ModelMapper getModelMapper() {
        return modelMapper;
    }
}
