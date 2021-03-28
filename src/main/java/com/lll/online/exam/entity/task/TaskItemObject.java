package com.lll.online.exam.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItemObject {
    private Integer examPaperId;
    private String examPaperName;
    private Integer itemOrder;
}