package com.lll.online.exam.viewmodel.admin.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息返回体
 *
 * @author: Mr.Garlic
 * @date: 2021/3/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseVM {
    private Integer id;

    private String title;

    private String content;

    private String sendUserName;
    // 接受人的用户名称，"张三，李四，王五"
    private String receives;

    private Integer receiveUserCount;

    private Integer readCount;
    // 需转换
    private String createTime;
}