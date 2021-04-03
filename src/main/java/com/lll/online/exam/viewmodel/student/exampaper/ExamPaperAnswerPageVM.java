package com.lll.online.exam.viewmodel.student.exampaper;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷列表接受类
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswerPageVM extends BasePage {

    private Integer subjectId;

    private Integer createUser;
}