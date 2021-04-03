package com.lll.online.exam.viewmodel.student.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 限时试卷返回体
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLimitPaper extends PaperInfo {
    private String startTime;
    private String endTime;

}
