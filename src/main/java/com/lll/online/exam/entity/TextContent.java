package com.lll.online.exam.entity;

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
    
    private Integer id;
    
    private String content;
    
    private Date createTime;


}