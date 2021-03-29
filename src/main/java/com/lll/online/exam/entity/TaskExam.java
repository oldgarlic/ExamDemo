package com.lll.online.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (TaskExam)实体类
 *
 * @author oldGarlic
 * @since 2021-03-27 22:58:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExam implements Serializable {
    private static final long serialVersionUID = 164385746143855867L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String title;
    /**
    * 级别
    */
    private Integer gradeLevel;
    /**
    * 任务框架 内容为JSON
    */

    private Integer frameTextContentId;
    
    private Integer createUser;
    
    private Date createTime;
    
    private Boolean deleted;
    
    private String createUserName;


}