package com.lll.online.exam.service;


import com.lll.online.exam.entity.ExamPaperQuestionCustomerAnswer;
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
}