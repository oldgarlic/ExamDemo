package com.lll.online.exam.viewmodel.admin.diashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexVM {
    // 试卷总数
    private Integer examPaperCount;
    // 问题总数
    private Integer questionCount;
    // 做题总数
    private Integer doExamPaperCount;
    // 答题总数
    private Integer doQuestionCount;
    // 日活
    private List<Integer> mothDayUserActionValue;
    // 日答题
    private List<Integer> mothDayDoExamQuestionValue;
    // 当月的日期
    private List<String> mothDayText;
}