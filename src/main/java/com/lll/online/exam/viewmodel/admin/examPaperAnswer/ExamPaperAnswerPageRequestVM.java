package com.lll.online.exam.viewmodel.admin.examPaperAnswer;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答卷接受类
 *
 * @author: Mr.Garlic
 * @date: 2021/4/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswerPageRequestVM extends BasePage {
    private Integer subjectId;
}