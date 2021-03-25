package com.lll.online.exam.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Question)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Repository
public interface QuestionMapper extends BaseMapper<Question> {


}