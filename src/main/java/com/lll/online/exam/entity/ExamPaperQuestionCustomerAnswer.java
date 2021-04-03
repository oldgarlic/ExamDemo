package com.lll.online.exam.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (ExamPaperQuestionCustomerAnswer)实体类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperQuestionCustomerAnswer implements Serializable {
    private static final long serialVersionUID = 980907393017253080L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 题目Id
    */
    private Integer questionId;
    /**
    * 答案Id
    */
    private Integer examPaperId;
    
    private Integer examPaperAnswerId;
    /**
    * 题型
    */
    private Integer questionType;
    /**
    * 学科
    */
    private Integer subjectId;
    /**
    * 得分
    */
    private Integer customerScore;
    /**
    * 题目原始分数
    */
    private Integer questionScore;
    /**
    * 问题内容
    */
    private Integer questionTextContentId;
    /**
    * 做题答案
    */
    private String answer;
    /**
    * 做题内容
    */
    private Integer textContentId;
    /**
    * 是否正确
    */
    private Boolean doRight;
    /**
    * 做题人
    */
    private Integer createUser;
    
    private Date createTime;
    
    private Integer itemOrder;


}