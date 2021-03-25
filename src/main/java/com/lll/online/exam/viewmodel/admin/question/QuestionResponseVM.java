package com.lll.online.exam.viewmodel.admin.question;

import com.lll.online.exam.viewmodel.BaseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Question返回类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseVM extends BaseVM {
    private Integer id;

    private Integer questionType;

    private Integer subjectId;

    private String score;

    private Integer gradeLevel;

    private Integer difficult;

    private String correct;

    private Integer infoTextContentId;

    private Integer createUser;

    private Integer status;

    private String createTime;
    // page查询的时候值 = null
    private Integer textContentId;
    // page查询的时候值 = null
    private Integer analyzeTextContentId;
    // page查询的时候需要访问t_text_content表，进行字符创解析
    private String shortTitle;
}
