package com.lll.online.exam.viewmodel.admin.taskExam;

import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * TaskExam修改或新建接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskEditRequestVM {

    private Integer id;
    @NotNull
    private Integer gradeLevel;
    @NotBlank
    private String title;
    @Valid
    @Size(min = 1,message = "请添加试卷")
    private List<ExamPaperResponseVM> paperItems;
}
