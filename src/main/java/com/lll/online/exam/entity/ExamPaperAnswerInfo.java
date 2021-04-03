package com.lll.online.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 答卷详情
 *
 * @author: Mr.Garlic
 * @date: 2021/3/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswerInfo {
    public ExamPaper examPaper;
    public ExamPaperAnswer examPaperAnswer;
    public List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers;
}