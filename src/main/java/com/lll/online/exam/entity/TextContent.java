package com.lll.online.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (TextContent)实体类
 *
 * @author oldGarlic
 * @since 2021-03-25 20:58:59
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextContent implements Serializable {
    private static final long serialVersionUID = 624630027386404619L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String content;
    
    private Date createTime;

    public TextContent(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }
}