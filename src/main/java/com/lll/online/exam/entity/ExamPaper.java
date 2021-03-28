package com.lll.online.exam.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (ExamPaper)实体类
 *
 * @author oldGarlic
 * @since 2021-03-22 15:25:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaper implements Serializable {
    private static final long serialVersionUID = 194622347656011389L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 试卷名称
    */
    private String name;
    /**
    * 学科
    */
    private Integer subjectId;
    /**
    * 试卷类型( 1固定试卷  2临时试卷 3班级试卷 4.时段试卷 5.推送试卷)
    */
    private Integer paperType;
    /**
    * 级别
    */
    private Integer gradeLevel;
    /**
    * 试卷总分(千分制)
    */
    private Integer score;
    /**
    * 题目数量
    */
    private Integer questionCount;
    /**
    * 建议时长(分钟)
    */
    private Integer suggestTime;
    /**
    * 时段试卷 开始时间
    */
    private Date limitStartTime;
    /**
    * 时段试卷 结束时间
    */
    private Date limitEndTime;
    /**
    * 试卷框架 内容为JSON
    */
    private Integer frameTextContentId;
    
    private Integer createUser;
    
    private Date createTime;
    
    private Boolean deleted;
    
    private Integer taskExamId;


}