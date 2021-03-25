package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.mapper.TextContentMapper;
import com.lll.online.exam.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (TextContent)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-25 20:58:59
 */
@Service("textContentService")
public class TextContentServiceImpl extends BaseServiceImpl<TextContent> implements TextContentService {

    private TextContentMapper textContentMapper;

    @Autowired
    public TextContentServiceImpl( TextContentMapper textContentMapper) {
        super(textContentMapper);
        this.textContentMapper = textContentMapper;
    }


}