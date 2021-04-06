package com.lll.online.exam.viewmodel.student.message;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息接受类
 *
 * @author: Mr.Garlic
 * @date: 2021/4/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestVM extends BasePage {

    private Integer receiveUserId;
}