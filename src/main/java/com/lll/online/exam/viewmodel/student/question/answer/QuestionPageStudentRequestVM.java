package com.lll.online.exam.viewmodel.student.question.answer;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答题接受体
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPageStudentRequestVM extends BasePage {
    private Integer createUser;
}
