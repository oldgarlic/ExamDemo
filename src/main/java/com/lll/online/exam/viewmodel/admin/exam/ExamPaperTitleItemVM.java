package com.lll.online.exam.viewmodel.admin.exam;

import com.lll.online.exam.viewmodel.admin.question.QuestionEditRequestVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 试卷细节类：需要从ExamPaper中的text_content_id去查询textContent表转换
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperTitleItemVM {

    @NotBlank(message = "标题名称不能为空")
    private String name;
    @Valid
    @Size(min = 1,message = "请添加题目")
    private List<QuestionEditRequestVM> questionItems;
}
