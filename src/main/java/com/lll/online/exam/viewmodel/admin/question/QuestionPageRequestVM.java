package com.lll.online.exam.viewmodel.admin.question;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Question分页接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPageRequestVM extends BasePage {
    private Integer id;
    private Integer level;
    private Integer questionType;
    private Integer subjectId;
}
