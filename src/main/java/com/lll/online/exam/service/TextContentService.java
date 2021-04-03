package com.lll.online.exam.service;

import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.task.TaskItemAnswerObject;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * (TextContent)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-25 20:58:59
 */
public interface TextContentService extends BaseService<TextContent>{

    /**
     * 创建一个TextContent，将内容转化为json，回写到content中，不入库
     *
     * @param list
     * @param now
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    <T, R> TextContent jsonConvertInsert(List<T> list, Date now, Function<? super T, ? extends R> mapper);

    /**
     * 修改一个TextContent，将内容转化为json，回写到content中，不入库
     *
     * @param textContent
     * @param list
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    <T, R> TextContent jsonConvertUpdate(TextContent textContent, List<T> list, Function<? super T, ? extends R> mapper);
}