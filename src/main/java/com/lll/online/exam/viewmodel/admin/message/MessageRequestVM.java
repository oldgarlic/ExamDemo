package com.lll.online.exam.viewmodel.admin.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 消息接受体
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestVM  {
    @NotBlank
    private String content;
    @NotBlank
    private String title;
    @Size(min = 1,message = "请添加发送用户")
    private List<Integer> receiveUserIds;
}
