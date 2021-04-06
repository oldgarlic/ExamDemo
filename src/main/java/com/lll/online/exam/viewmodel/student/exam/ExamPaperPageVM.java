package com.lll.online.exam.viewmodel.student.exam;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 试卷列表接受类
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperPageVM extends BasePage {
    @NotNull
    private Integer paperType;
    private Integer subjectId;
    private Integer levelId;
}