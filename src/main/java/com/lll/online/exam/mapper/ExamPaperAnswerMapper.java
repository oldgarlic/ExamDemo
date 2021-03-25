package com.lll.online.exam.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.ExamPaperAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (ExamPaperAnswer)表数据库访问层
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Repository
public interface ExamPaperAnswerMapper extends BaseMapper<ExamPaperAnswer> {


}