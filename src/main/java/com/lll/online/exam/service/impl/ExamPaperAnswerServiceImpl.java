package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.mapper.ExamPaperAnswerMapper;
import com.lll.online.exam.service.ExamPaperAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ExamPaperAnswer)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("examPaperAnswerService")
public class ExamPaperAnswerServiceImpl extends BaseServiceImpl<ExamPaperAnswer> implements ExamPaperAnswerService {

    private ExamPaperAnswerMapper examPaperAnswerMapper;

    @Autowired
    public ExamPaperAnswerServiceImpl(ExamPaperAnswerMapper examPaperAnswerMapper) {
        super(examPaperAnswerMapper);
        this.examPaperAnswerMapper = examPaperAnswerMapper;
    }

    @Override
    public Integer getAllCount() {
        Integer count = examPaperAnswerMapper.selectCount(null);
        return count;
    }
}