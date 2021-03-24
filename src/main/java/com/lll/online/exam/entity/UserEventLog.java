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
 * (UserEventLog)实体类
 *
 * @author oldGarlic
 * @since 2021-03-18 11:05:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEventLog implements Serializable {
    private static final long serialVersionUID = -47207250267512197L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
    * 用户id
    */
    private Integer userId;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 真实姓名
    */
    private String realName;
    /**
    * 内容
    */
    private String content;
    /**
    * 时间
    */
    private Date createTime;

    public UserEventLog(Integer userId, String userName, String realName, Date createTime) {
        this.userId = userId;
        this.userName = userName;
        this.realName = realName;
        this.createTime = createTime;
    }
}