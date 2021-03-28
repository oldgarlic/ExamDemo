package com.lll.online.exam.viewmodel.admin.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperResponseVM {
    private Integer id;

    private String name;

    private Integer questionCount;

    private Integer score;
    // 需转换
    private String createTime;

    private Integer createUser;

    private Integer subjectId;

    private Integer paperType;

    private Integer frameTextContentId;
}