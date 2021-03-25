package com.lll.online.exam.entity;


import java.util.Date;
import java.io.Serializable;

/**
 * (Message)实体类
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:48
 */

public class Message implements Serializable {
    private static final long serialVersionUID = -35360251711434617L;
    
    private Integer id;
    /**
    * 标题
    */
    private String title;
    /**
    * 内容
    */
    private String content;
    
    private Date createTime;
    /**
    * 发送者用户ID
    */
    private Integer sendUserId;
    /**
    * 发送者用户名
    */
    private String sendUserName;
    /**
    * 发送者真实姓名
    */
    private String sendRealName;
    /**
    * 接收人数
    */
    private Integer receiveUserCount;
    /**
    * 已读人数
    */
    private Integer readCount;


}