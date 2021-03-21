package com.lll.online.exam.base;

import com.lll.online.exam.content.WebContent;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.utility.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础controller类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
public class BaseController {
    @Autowired
    protected WebContent webContent;

    protected final static ModelMapper modelMapper = ModelMapperUtil.instance();

    public User getCurrentUser(){
        return webContent.getCurrentUser();
    }
}
