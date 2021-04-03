package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.mapper.ExamPaperAnswerMapper;
import com.lll.online.exam.service.ExamPaperAnswerService;
import com.lll.online.exam.viewmodel.student.exampaper.ExamPaperAnswerPageVM;
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
    public ExamPaperAnswer selectByPidUid(Integer pid, Integer uid) {
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_id",pid).eq("create_user",uid);
        ExamPaperAnswer examPaperAnswer = examPaperAnswerMapper.selectOne(queryWrapper);
        return examPaperAnswer;
    }

    @Override
    public Integer getAllCount() {
        Integer count = examPaperAnswerMapper.selectCount(null);
        return count;
    }

    @Override
    public IPage<ExamPaperAnswer> pageList(ExamPaperAnswerPageVM model) {
        Page<ExamPaperAnswer> page = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<ExamPaperAnswer> queryWrapper = new QueryWrapper<>();
        if(model.getCreateUser()!=null){
            queryWrapper.eq("create_user",model.getCreateUser());
        }
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }
        IPage<ExamPaperAnswer> examPaperAnswerIPage = examPaperAnswerMapper.selectPage(page, queryWrapper);
        return examPaperAnswerIPage;
    }
}