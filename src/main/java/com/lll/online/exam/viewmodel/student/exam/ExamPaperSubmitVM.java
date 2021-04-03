package com.lll.online.exam.viewmodel.student.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * 试卷提交接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperSubmitVM {
    private Integer id;
    private Integer doTime;
    // 这个是干啥的
    private String score;

    private List<ExamPaperSubmitItemVM> answerItems;
}
