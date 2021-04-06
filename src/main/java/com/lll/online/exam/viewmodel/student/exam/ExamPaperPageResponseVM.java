package com.lll.online.exam.viewmodel.student.exam;

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
public class ExamPaperPageResponseVM {
    private Integer id;

    private String name;

    private Integer questionCount;
    // 需转换
    private Integer score;
    //需转换
    private String createTime;

    private Integer createUser;

    private Integer subjectId;
    // 需转换
    private String subjectName;

    private Integer paperType;

    private Integer frameTextContentId;
}