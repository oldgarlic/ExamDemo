package com.lll.online.exam.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lll.online.exam.entity.enums.QuestionTypeEnum;
import com.lll.online.exam.utility.ExamUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (Question)实体类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
    private static final long serialVersionUID = 236212924138672083L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 1.单选题  2.多选题  3.判断题 4.填空题 5.简答题
    */
    private Integer questionType;
    /**
    * 学科
    */
    private Integer subjectId;
    /**
    * 题目总分(千分制)
    */
    private Integer score;
    /**
    * 年级
    */
    private Integer gradeLevel;
    /**
    * 题目难度
    */
    private Integer difficult;
    /**
    * 正确答案
    */
    private String correct;
    /**
    * 题目  填空、 题干、解析、答案等信息
    */
    private Integer infoTextContentId;
    /**
    * 创建人
    */
    private Integer createUser;
    /**
    * 1.正常
    */
    private Integer status;
    /**
    * 创建时间
    */
    private Date createTime;
    
    private Boolean deleted;

    public void setCorrectFromVM(String correct, List<String> correctArray){
        // 多选题
        if(this.getQuestionType() == QuestionTypeEnum.MultipleChoice.getCode()){
            this.setCorrect(ExamUtil.contentToString(correctArray));
        }else{
            this.setCorrect(correct);
        }
    }
}