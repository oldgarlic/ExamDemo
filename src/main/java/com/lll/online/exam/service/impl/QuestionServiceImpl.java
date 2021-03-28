package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.Question;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.QuestionStatusEnum;
import com.lll.online.exam.entity.enums.QuestionTypeEnum;
import com.lll.online.exam.entity.question.QuestionItemObject;
import com.lll.online.exam.entity.question.QuestionObject;
import com.lll.online.exam.mapper.QuestionMapper;
import com.lll.online.exam.mapper.TextContentMapper;
import com.lll.online.exam.service.QuestionService;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditItemVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Question)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("questionService")
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {

    private QuestionMapper questionMapper;
    private TextContentMapper textContentMapper;
    private final ModelMapper modelMapper = ModelMapperUtil.instance();

    @Autowired
    public QuestionServiceImpl( QuestionMapper questionMapper,TextContentMapper textContentMapper) {
        super(questionMapper);
        this.questionMapper = questionMapper;
        this.textContentMapper = textContentMapper;
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

    @Transactional
    @Override
    public Integer insertQuestion(QuestionEditRequestVM model, User currentUser) {
        // TODO：涉及到的表，question和text_content
        // TODO：TextContent的content是一个QuestionObject对象的jsonString
        Date now = new Date();
        TextContent textContent = new TextContent();
        textContent.setCreateTime(now);
        // QuestionObject
        setQuestionTextContentFormVM(textContent,model);
        textContentMapper.insert(textContent);


        Question question = new Question();
        // 不同题目类型的correct是不一样的
        // 单选的correct在correct，多选的correct在correctArray

        question.setCreateTime(now);
        question.setCreateUser(currentUser.getId());
        question.setDeleted(false);
        question.setGradeLevel(model.getGradeLevel());
        // 这里
        question.setInfoTextContentId(textContent.getId());
        question.setDifficult(model.getDifficult());
        question.setScore(ExamUtil.scoreFromVM(model.getScore()));
        question.setStatus(QuestionStatusEnum.OK.getCode());
        question.setQuestionType(model.getQuestionType());
        question.setSubjectId(model.getSubjectId());

        question.setCorrectFromVM(model.getCorrect(),model.getCorrectArray());
        questionMapper.insert(question);
        return null;
    }
    /*
    * @Description: 把QuestionEditRequestVM.items和部分信息转化为QuestionObject的JSONString
    * @Date: 2021/3/27
    */
    private void setQuestionTextContentFormVM(TextContent textContent, QuestionEditRequestVM model) {

        List<QuestionItemObject> list = model.getItems().stream().map(t->{
            QuestionItemObject questionItemObject = new QuestionItemObject();
            questionItemObject.setContent(t.getContent());
            questionItemObject.setPrefix(t.getPrefix());
            questionItemObject.setScore(ExamUtil.scoreFromVM(t.getScore()));
            return questionItemObject;
        }).collect(Collectors.toList());

        QuestionObject questionObject = new QuestionObject();
        questionObject.setQuestionItemObjects(list);
        questionObject.setAnalyze(model.getAnalyze());
        questionObject.setCorrect(model.getCorrect());
        questionObject.setTitleContent(model.getTitle());

        textContent.setContent(JsonUtil.toJsonStr(questionObject));
    }

    @Transactional
    @Override
    public Integer updateQuestion(QuestionEditRequestVM model) {
        // TODO：涉及到的表，question和text_content
        Question question = questionMapper.selectById(model.getId());

        question.setCorrectFromVM(model.getCorrect(),model.getCorrectArray());
        question.setGradeLevel(model.getGradeLevel());
        question.setDifficult(model.getDifficult());
        question.setScore(ExamUtil.scoreFromVM(model.getScore()));
        question.setSubjectId(model.getSubjectId());

        questionMapper.updateById(question);


        TextContent textContent = textContentMapper.selectById(question.getInfoTextContentId());
        setQuestionTextContentFormVM(textContent,model);

        textContentMapper.updateById(textContent);


        return null;
    }

    @Override
    public Integer deleteById(Integer id) {
        Question question = questionMapper.selectById(id);
        if(question==null){
            return null;
        }
        question.setDeleted(true);
        questionMapper.updateById(question);
        return null;
    }

    @Override
    public QuestionEditRequestVM getQuestionEditRequestVM(Integer id) {

        //TODO：Go
        Question question = questionMapper.selectById(id);
        TextContent textContent = textContentMapper.selectById(question.getInfoTextContentId());
        QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
        QuestionEditRequestVM questionEditRequestVM = modelMapper.map(question, QuestionEditRequestVM.class);

        questionEditRequestVM.setScore(ExamUtil.scoreToVM(question.getScore()));
        questionEditRequestVM.setAnalyze(questionObject.getAnalyze());
        questionEditRequestVM.setTitle(questionObject.getTitleContent());
        // 不同题目类型不同操作
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.getQuestionTypeEnum(question.getQuestionType());
        switch (questionTypeEnum){
            // 答案放在 correct
            case TrueFalse:
            case SingleChoice:
            case ShortAnswer:
                questionEditRequestVM.setCorrect(question.getCorrect());
                break;
            // 答案放在correctArray
            case GapFilling:
                List<String> correctContent = questionObject.getQuestionItemObjects().stream().map(d -> d.getContent()).collect(Collectors.toList());
                questionEditRequestVM.setCorrectArray(correctContent);
                break;
            // 答案放在correctArray
            case MultipleChoice:
                questionEditRequestVM.setCorrectArray(ExamUtil.contentToArray(question.getCorrect()));
                break;
            default:
                break;
        }
        // 不同答案设置不同地方
        // 单选/对错题 question-correct
        // 多选 question-correct
        // 填空 questionObjectItem-
        // 简答 questionObject中的correct


        List<QuestionEditItemVM> collect = questionObject.getQuestionItemObjects().stream().map(t -> {
            QuestionEditItemVM item = modelMapper.map(t, QuestionEditItemVM.class);
            if(t.getScore()!=null){
                item.setScore(ExamUtil.scoreToVM(t.getScore()));
            }
            return item;
        }).collect(Collectors.toList());
        questionEditRequestVM.setItems(collect);

        return questionEditRequestVM;
    }
}