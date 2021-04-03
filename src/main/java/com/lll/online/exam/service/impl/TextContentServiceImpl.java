package com.lll.online.exam.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.mapper.TextContentMapper;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public TextContentServiceImpl(TextContentMapper textContentMapper) {
        super(textContentMapper);
        this.textContentMapper = textContentMapper;
    }


    @Override
    public <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper) {
        String frameTextContent = null;
        if (null == mapper) {
            frameTextContent = JsonUtil.toJsonStr(list);
        } else {
            List<R> mapList = list.stream().map(mapper).collect(Collectors.toList());
            frameTextContent = JsonUtil.toJsonStr(mapList);
        }
        TextContent textContent = new TextContent(frameTextContent, now);
        //insertByFilter(textContent);  cache useless
        return textContent;
    }

    @Override
    public <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper) {
        return null;
    }
}