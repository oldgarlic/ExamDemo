package com.lll.online.exam.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (MessageUser)实体类
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUser implements Serializable {
    private static final long serialVersionUID = -18177706262425966L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 消息内容ID
    */
    private Integer messageId;
    /**
    * 接收人ID
    */
    private Integer receiveUserId;
    /**
    * 接收人用户名
    */
    private String receiveUserName;
    /**
    * 接收人真实姓名
    */
    private String receiveRealName;
    /**
    * 是否已读
    */
    private Boolean readed;
    
    private Date createTime;
    /**
    * 阅读时间
    */
    private Date readTime;


}