package com.lll.online.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.TextContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (TextContent)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-25 20:58:59
 */
@Repository
public interface TextContentMapper extends BaseMapper<TextContent> {


}