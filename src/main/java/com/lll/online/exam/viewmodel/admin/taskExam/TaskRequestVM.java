package com.lll.online.exam.viewmodel.admin.taskExam;

import com.lll.online.exam.viewmodel.admin.exam.ExamPaperResponseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestVM {

    private Integer id;

    @NotNull
    private Integer gradeLevel;


    @NotNull
    private String title;

    // textContent里面解析出来
    @Size(min = 1, message = "请添加试卷")
    @Valid
    private List<ExamPaperResponseVM> paperItems;
}