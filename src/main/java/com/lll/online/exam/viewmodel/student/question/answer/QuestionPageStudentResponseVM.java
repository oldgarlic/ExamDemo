package com.lll.online.exam.viewmodel.student.question.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答题返回体
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPageStudentResponseVM {
    private Integer id;

    private Integer questionType;
    // 需转换
    private String createTime;
    // 需查询
    private String subjectName;
    // 需转换
    private String shortTitle;
}