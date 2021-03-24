package com.lll.online.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Subject)实体类
 *
 * @author oldGarlic
 * @since 2021-03-24 21:12:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {
    private static final long serialVersionUID = 873682825744414485L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 语文 数学 英语 等
    */
    private String name;
    /**
    * 年级 (1-12) 小学 初中 高中  大学
    */
    private Integer level;
    /**
    * 一年级、二年级等
    */
    private String levelName;
    /**
    * 排序
    */
    private Integer itemOrder;
    
    private Boolean deleted;


}