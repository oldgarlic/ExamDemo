package com.lll.online.exam.viewmodel.admin.taskExam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskPageResponseVM {

    private Integer id;

    private String title;

    private Integer gradeLevel;

    private String createUserName;
    // 需转换
    private String createTime;

    private Boolean deleted;
}
