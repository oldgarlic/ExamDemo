package com.lll.online.exam.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (ExamPaperAnswer)实体类：一份试卷答案对应一个ExamPaperAnswer
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaperAnswer implements Serializable {
    private static final long serialVersionUID = 279020315605308585L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer examPaperId;
    /**
    * 试卷名称
    */
    private String paperName;
    /**
    * 试卷类型( 1固定试卷  2临时试卷 3班级试卷 4.时段试卷 )
    */
    private Integer paperType;
    /**
    * 学科
    */
    private Integer subjectId;
    /**
    * 系统判定得分
    */
    private Integer systemScore;
    /**
    * 最终得分(千分制)
    */
    private Integer userScore;
    /**
    * 试卷总分
    */
    private Integer paperScore;
    /**
    * 做对题目数量
    */
    private Integer questionCorrect;
    /**
    * 题目总数量
    */
    private Integer questionCount;
    /**
    * 做题时间(秒)
    */
    private Integer doTime;
    /**
    * 试卷状态(1待判分 2完成)
    */
    private Integer status;
    /**
    * 学生
    */
    private Integer createUser;
    /**
    * 提交时间
    */
    private Date createTime;
    
    private Integer taskExamId;


}