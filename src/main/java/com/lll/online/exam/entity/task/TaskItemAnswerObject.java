package com.lll.online.exam.entity.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷答案JSONString
 *
 * @author: Mr.Garlic
 * @date: 2021/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskItemAnswerObject {
    private Integer examPaperId;
    private Integer examPaperAnswerId;
    private Integer status;
}