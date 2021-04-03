package com.lll.online.exam.viewmodel.student.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷过滤器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperFilter {
    private Integer paperType;
    private Integer gradeLevel;
}
