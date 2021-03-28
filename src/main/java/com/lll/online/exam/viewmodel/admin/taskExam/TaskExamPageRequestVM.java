package com.lll.online.exam.viewmodel.admin.taskExam;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TaskExam分页接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExamPageRequestVM extends BasePage {
    private Integer gradeLevel;
}
