package com.lll.online.exam.viewmodel.student.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息返回体：通过查询t_message和t_message_user两表解决问题
 *
 * @author ：Mr.Garlic
 * @date ： 2021/4/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseVM {
    private Integer id;
    // 需查询
    private String title;

    private Integer messageId;
    // 需查询
    private String content;

    private Boolean readed;
    // 需转换
    private String createTime;
    // 需查询
    private String sendUserName;
}
