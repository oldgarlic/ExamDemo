package com.lll.online.exam.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lll.online.exam.entity.ExamPaperQuestionCustomerAnswer;
import com.lll.online.exam.entity.other.ExamPaperAnswerUpdate;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import com.lll.online.exam.viewmodel.student.question.answer.QuestionPageStudentRequestVM;

import java.util.List;

/**
 * (ExamPaperQuestionCustomerAnswer)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
public interface ExamPaperQuestionCustomerAnswerService extends BaseService<ExamPaperQuestionCustomerAnswer>{


    Integer getAllCount();

    List<Integer> getMonthCount();

    void insertList(List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers);

    IPage<ExamPaperQuestionCustomerAnswer> studentPage(QuestionPageStudentRequestVM model);

    ExamPaperSubmitItemVM examPaperQuestionCustomerAnswerToVM(ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer);

    List<ExamPaperQuestionCustomerAnswer> selectListByPaperAnswerId(Integer id);

    void updateScore(List<ExamPaperAnswerUpdate> examPaperAnswerUpdates);
}