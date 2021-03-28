package com.lll.online.exam.viewmodel.admin.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 添加或修改试卷接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperEditRequestVM {

    private Integer id;
    @NotNull
    private Integer level;

    private List<String> limitDateTime;
    @NotBlank
    private String name;
    @NotNull
    private Integer subjectId;
    @NotNull
    private int suggestTime;
    @NotNull
    private Integer paperType;

    @Valid
    @Size(min = 1,message = "请添加试卷标题")
    private List<ExamPaperTitleItemVM> titleItems;
}
