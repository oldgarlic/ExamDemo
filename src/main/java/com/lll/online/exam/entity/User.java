package com.lll.online.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author oldGarlic
 * @since 2021-03-16 20:25:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = -11820282133101263L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String userUuid;
    /**
    * 用户名
    */
    private String userName;
    
    private String password;
    /**
    * 真实姓名
    */
    private String realName;
    
    private Integer age;
    /**
    * 1.男 2女
    */
    private Integer sex;
    
    private Date birthDay;
    /**
    * 学生年级(1-12)
    */
    private Integer userLevel;
    
    private String phone;
    /**
    * 1.学生 2.老师 3.管理员
    */
    private Integer role;
    /**
    * 1.启用 2禁用
    */
    private Integer status;
    /**
    * 头像地址
    */
    private String imagePath;
    
    private Date createTime;
    
    private Date modifyTime;
    
    private Date lastActiveTime;
    /**
    * 是否删除
    */
    private Boolean deleted;
    /**
    * 微信openId
    */
    private String wxOpenId;


}