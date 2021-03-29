package com.lll.online.exam.viewmodel.admin.message;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息分页查询接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagePageRequestVM extends BasePage {
    private String sendUserName;
}
