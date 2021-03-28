package com.lll.online.exam.viewmodel.admin.exam;

import com.lll.online.exam.base.BasePage;
import com.lll.online.exam.viewmodel.BaseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷分页查询接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperPageRequestVM extends BasePage {
    private Integer id;
    private Integer level;
    private Integer subjectId;
}
