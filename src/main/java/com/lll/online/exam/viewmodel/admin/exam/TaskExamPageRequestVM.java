package com.lll.online.exam.viewmodel.admin.exam;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 任务试卷接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExamPageRequestVM extends BasePage {
    private Integer id;
    private Integer subjectId;
    @NotNull
    private Integer level;
    @NotNull
    private Integer paperType;
}
