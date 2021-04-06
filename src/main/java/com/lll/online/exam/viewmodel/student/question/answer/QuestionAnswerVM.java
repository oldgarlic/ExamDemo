package com.lll.online.exam.viewmodel.student.question.answer;

import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.lll.online.exam.viewmodel.student.exam.ExamPaperSubmitItemVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 错题查看返回体
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerVM {
    private QuestionEditRequestVM questionVM;
    private ExamPaperSubmitItemVM questionAnswerVM;
}