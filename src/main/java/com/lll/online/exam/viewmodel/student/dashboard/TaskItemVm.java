package com.lll.online.exam.viewmodel.student.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItemVm {
    private Integer id;
    private String title;
    private List<TaskItemPaperVm> paperItems;
}