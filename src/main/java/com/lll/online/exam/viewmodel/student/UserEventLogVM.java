package com.lll.online.exam.viewmodel.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户事件日志返回类
 *
 * @author: Mr.Garlic
 * @date: 2021/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEventLogVM {

    private Integer id;

    private Integer userId;

    private String userName;

    private String realName;

    private String content;

    private String createTime;
}