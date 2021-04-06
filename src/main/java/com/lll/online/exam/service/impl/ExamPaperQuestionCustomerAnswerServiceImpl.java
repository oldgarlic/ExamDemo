package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.entity.ExamPaperAnswer;
import com.lll.online.exam.entity.ExamPaperQuestionCustomerAnswer;
import com.lll.online.exam.entity.TextContent;
import com.lll.online.exam.entity.enums.QuestionTypeEnum;
import com.lll.online.exam.entity.other.ExamPaperAnswerUpdate;
import com.lll.online.exam.entity.other.KeyValue;
import com.lll.online.exam.mapper.ExamPaperQuestionCustomerAnswerMapper;
import com.lll.online.exam.service.ExamPaperQuestionCustomerAnswerService;
import com.lll.online.exam.service.TextContentService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ExamUtil;
import com.lll.online.exam.utility.JsonUtil;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.lll.online.exam.viewmodel.student.question.answer.QuestionPageStudentRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (ExamPaperQuestionCustomerAnswer)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Service("examPaperQuestionCustomerAnswerService")
public class ExamPaperQuestionCustomerAnswerServiceImpl extends BaseServiceImpl<ExamPaperQuestionCustomerAnswer>
                                                                    implements ExamPaperQuestionCustomerAnswerService {

    private ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper;
    private TextContentService textContentService;

    @Autowired
    public ExamPaperQuestionCustomerAnswerServiceImpl(ExamPaperQuestionCustomerAnswerMapper examPaperQuestionCustomerAnswerMapper,TextContentService textContentService) {
        super(examPaperQuestionCustomerAnswerMapper);
        this.examPaperQuestionCustomerAnswerMapper = examPaperQuestionCustomerAnswerMapper;
        this.textContentService = textContentService;
    }

    @Override
    public Integer getAllCount() {
        return examPaperQuestionCustomerAnswerMapper.selectCount(null);
    }

    /*
    * @Description: 获得日答题数
    * @Param: []
    * @return: java.util.List<java.lang.Integer>
    * @Date: 2021/3/22
    */
    @Override
    public List<Integer> getMonthCount() {
        Date startDate = DateTimeUtil.getMonthStartDate();
        Date endDate = DateTimeUtil.getMonthEndDate();
        // TODO：sql写得有问题？？？
        List<KeyValue> list = examPaperQuestionCustomerAnswerMapper.selectCountByDate(startDate,endDate);
        List<String> startToNowDate = DateTimeUtil.getStartToNowDate();

        List<Integer> result =  startToNowDate.stream().map(md -> {
            KeyValue keyValue = list.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers) {
        examPaperQuestionCustomerAnswerMapper.insertList(examPaperQuestionCustomerAnswers);
    }

    @Override
    public IPage<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM model) {
        Page<ExamPaperQuestionCustomerAnswer> page = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("create_user",model.getCreateUser()).eq("do_right",false);

        IPage<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswerIPage = examPaperQuestionCustomerAnswerMapper.selectPage(page, queryWrapper);
        return examPaperQuestionCustomerAnswerIPage;
    }

    @Override
    public ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer) {
        ExamPaperSubmitItemVM examPaperSubmitItemVM = new ExamPaperSubmitItemVM();
        examPaperSubmitItemVM.setId(examPaperQuestionCustomerAnswer.getId());
        examPaperSubmitItemVM.setQuestionId(examPaperQuestionCustomerAnswer.getQuestionId());
        examPaperSubmitItemVM.setDoRight(examPaperQuestionCustomerAnswer.getDoRight());
        examPaperSubmitItemVM.setItemOrder(examPaperQuestionCustomerAnswer.getItemOrder());
        examPaperSubmitItemVM.setQuestionScore(ExamUtil.scoreToVM(examPaperQuestionCustomerAnswer.getQuestionScore()));
        examPaperSubmitItemVM.setScore(ExamUtil.scoreToVM(examPaperQuestionCustomerAnswer.getCustomerScore()));
        setSpecialToVM(examPaperSubmitItemVM, examPaperQuestionCustomerAnswer);
        return examPaperSubmitItemVM;
    }

    @Override
    public List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id) {
        QueryWrapper<ExamPaperQuestionCustomerAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("exam_paper_answer_id",id);
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperQuestionCustomerAnswerMapper.selectList(queryWrapper);
        return examPaperQuestionCustomerAnswers;
    }

    @Override
    public void updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates) {
        examPaperQuestionCustomerAnswerMapper.updateScore(examPaperAnswerUpdates);
    }

    private void setSpecialToVM(ExamPaperSubmitItemVM examPaperSubmitItemVM, ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.getQuestionTypeEnum(examPaperQuestionCustomerAnswer.getQuestionType());
        switch (questionTypeEnum) {
            case MultipleChoice:
                examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                examPaperSubmitItemVM.setContentArray(ExamUtil.contentToArray(examPaperQuestionCustomerAnswer.getAnswer()));
                break;
            case GapFilling:
                TextContent textContent = textContentService.selectById(examPaperQuestionCustomerAnswer.getTextContentId());
                List<String> correctAnswer = JsonUtil.toJsonListObject(textContent.getContent(), String.class);
                examPaperSubmitItemVM.setContentArray(correctAnswer);
                break;
            default:
                if (QuestionTypeEnum.needSaveTextContent(examPaperQuestionCustomerAnswer.getQuestionType())) {
                    TextContent content = textContentService.selectById(examPaperQuestionCustomerAnswer.getTextContentId());
                    examPaperSubmitItemVM.setContent(content.getContent());
                } else {
                    examPaperSubmitItemVM.setContent(examPaperQuestionCustomerAnswer.getAnswer());
                }
                break;
        }
    }

}