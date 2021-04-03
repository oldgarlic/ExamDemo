package com.lll.online.exam.viewmodel.student.exampaper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷列表返回体
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswerPageResponseVM {
    private Integer id;

    // 需转换
    private String createTime;

    private String userScore;

    // 需查询
    private String subjectName;

    private Integer subjectId;

    private Integer questionCount;

    private Integer questionCorrect;

    private String paperScore;

    // 需转换
    private String doTime;

    private Integer paperType;

    private String systemScore;

    private Integer status;

    private String paperName;
    // 需查询
    private String userName;
}