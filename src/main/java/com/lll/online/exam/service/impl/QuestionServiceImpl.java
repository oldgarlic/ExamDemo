package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.Question;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.mapper.QuestionMapper;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Question)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {

    private QuestionMapper questionMapper;

    @Autowired
    public QuestionServiceImpl( QuestionMapper questionMapper) {
        super(questionMapper);
        this.questionMapper = questionMapper;
    }

    @Override
    public Integer getAllCount() {
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted",0);
        Integer count = questionMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public IPage<Question> pageList(QuestionPageRequestVM model) {
        Page<Question> questionPage = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if(model.getId()!=null){
            queryWrapper.eq("id",model.getId());
        }
        if(model.getLevel()!=null){
            queryWrapper.eq("grade_level",model.getLevel());
        }
        if(model.getQuestionType()!=null){
            queryWrapper.eq("question_type",model.getQuestionType());
        }
        if(model.getSubjectId()!=null){
            queryWrapper.eq("subject_id",model.getSubjectId());
        }

        IPage<Question> questionIPage = questionMapper.selectPage(questionPage, queryWrapper);
        return questionIPage;
    }

    @Override
    public Integer insertQuestion(QuestionEditRequestVM model, User currentUser) {
        // TODO：涉及到的表，question和text_content
        return null;
    }

    @Override
    public Integer updateQuestion(QuestionEditRequestVM model) {
        // TODO：涉及到的表，question和text_content
        return null;
    }
}