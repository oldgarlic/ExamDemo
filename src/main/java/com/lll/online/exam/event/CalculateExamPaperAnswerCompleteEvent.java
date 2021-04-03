package com.lll.online.exam.event;

import com.lll.online.exam.entity.ExamPaperAnswerInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 答题事件
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/3
 */
public class CalculateExamPaperAnswerCompleteEvent extends ApplicationEvent {
    private  ExamPaperAnswerInfo ExamPaperAnswerInfo;

    public CalculateExamPaperAnswerCompleteEvent(ExamPaperAnswerInfo examPaperAnswerInfo) {
        super(examPaperAnswerInfo);
        ExamPaperAnswerInfo = examPaperAnswerInfo;
    }

    public com.lll.online.exam.entity.ExamPaperAnswerInfo getExamPaperAnswerInfo() {
        return ExamPaperAnswerInfo;
    }
}
