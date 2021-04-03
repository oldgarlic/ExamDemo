package com.lll.online.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (TaskExamCustomerAnswer)实体类
 *
 * @author oldGarlic
 * @since 2021-04-03 17:29:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskExamCustomerAnswer implements Serializable {
    private static final long serialVersionUID = 852149164413889468L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer taskExamId;
    
    private Integer createUser;
    
    private Date createTime;
    /**
    * 任务完成情况(Json)
    */
    private Integer textContentId;


}