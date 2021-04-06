package com.lll.online.exam.viewmodel.admin.examPaperAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答卷返回类
 *
 * @author: Mr.Garlic
 * @date: 2021/4/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswerPageResponseVM {
    private Integer id;
    // 需转换
    private String createTime;
    // 需转换
    private String userScore;
    // 需查询
    private String subjectName;

    private Integer subjectId;

    private Integer questionCount;

    private Integer questionCorrect;
    // 需转换
    private String paperScore;
    // 需转换
    private String doTime;

    private Integer paperType;
    // 需转换
    private String systemScore;

    private Integer status;

    private String paperName;
    // 需查询
    private String userName;
}