package com.lll.online.exam.listener;

import com.lll.online.exam.entity.*;
import com.lll.online.exam.entity.enums.ExamPaperTypeEnum;
import com.lll.online.exam.entity.enums.QuestionTypeEnum;
import com.lll.online.exam.event.CalculateExamPaperAnswerCompleteEvent;
import com.lll.online.exam.service.ExamPaperAnswerService;
import com.lll.online.exam.service.ExamPaperQuestionCustomerAnswerService;
import com.lll.online.exam.service.TaskExamCustomerAnswerService;
import com.lll.online.exam.service.TextContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.lll.online.exam.entity.enums.ExamPaperTypeEnum.Task;

/**
 * 答题事件监听
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/3
 */
@Component
public class CalculateExamPaperAnswerListener implements ApplicationListener<CalculateExamPaperAnswerCompleteEvent> {

    private final ExamPaperAnswerService examPaperAnswerService;
    private final ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final TextContentService textContentService;
    private final TaskExamCustomerAnswerService examCustomerAnswerService;

    @Autowired
    public CalculateExamPaperAnswerListener(ExamPaperAnswerService examPaperAnswerService, ExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, TextContentService textContentService, TaskExamCustomerAnswerService examCustomerAnswerService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.textContentService = textContentService;
        this.examCustomerAnswerService = examCustomerAnswerService;
    }

    @Override
    public void onApplicationEvent(CalculateExamPaperAnswerCompleteEvent calculateExamPaperAnswerCompleteEvent) {
        //TODO：
        ExamPaperAnswerInfo examPaperAnswerInfo = calculateExamPaperAnswerCompleteEvent.getExamPaperAnswerInfo();
        ExamPaper examPaper = examPaperAnswerInfo.getExamPaper();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        List<ExamPaperQuestionCustomerAnswer> examPaperQuestionCustomerAnswers = examPaperAnswerInfo.getExamPaperQuestionCustomerAnswers();
        Date now = new Date();

        examPaperAnswerService.insert(examPaperAnswer);

        examPaperQuestionCustomerAnswers.stream().filter(a -> QuestionTypeEnum.needSaveTextContent(a.getQuestionType())).forEach(d -> {
            TextContent textContent = new TextContent(d.getAnswer(), now);
            textContentService.insert(textContent);
            d.setTextContentId(textContent.getId());
            d.setAnswer(null);
        });

        examPaperQuestionCustomerAnswers.forEach(d -> {
            d.setExamPaperAnswerId(examPaperAnswer.getId());
        });
        // 3.
        examPaperQuestionCustomerAnswerService.insertList(examPaperQuestionCustomerAnswers);

        switch (ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            case Task: {
                examCustomerAnswerService.insertOrUpdate(examPaper, examPaperAnswer, now);
                break;
            }
            default:
                break;
        }

    }
}
