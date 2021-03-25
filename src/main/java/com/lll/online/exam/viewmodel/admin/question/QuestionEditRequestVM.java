package com.lll.online.exam.viewmodel.admin.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEditRequestVM {

    private Integer id;
    @NotNull
    private Integer questionType;
    @NotNull
    private Integer subjectId;


    private Integer gradeLevel;


    @Valid
    private List<QuestionEditItemVM> items;
    @NotBlank
    private String analyze;
    @NotBlank
    private String title;

    // 多选
    private List<String> correctArray;
    // 单选
    private String correct;
    @NotBlank
    private String score;

    @Range(min = 1, max = 5, message = "请选择题目难度")
    private Integer difficult;

    private Integer itemOrder;
}