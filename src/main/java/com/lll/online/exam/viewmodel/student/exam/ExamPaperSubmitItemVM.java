package com.lll.online.exam.viewmodel.student.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 答卷问题细节
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperSubmitItemVM {

    private Integer id;
    // doRight??
    private Boolean doRight;
    private String content;
    private List<String> contentArray;
    private Integer itemOrder;
    @NotNull
    private Integer questionId;

    // private Boolean doRight;
    // 下面这两个是啥
    private String score;
    private String questionScore;
}
