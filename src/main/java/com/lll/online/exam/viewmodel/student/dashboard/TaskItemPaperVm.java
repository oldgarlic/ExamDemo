package com.lll.online.exam.viewmodel.student.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItemPaperVm {
    private Integer examPaperId;
    private String examPaperName;
    private Integer examPaperAnswerId;
    private Integer status;
}
