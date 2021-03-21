package com.lll.online.exam.utility;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

/**
 * ModelMapper单例工具类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
public class ModelMapperUtil {
    private final static ModelMapper modelMapper = new ModelMapper();
    static {
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }
    public static ModelMapper instance(){
        return modelMapper;
    }
}
