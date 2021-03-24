package com.lll.online.exam.viewmodel.admin.user;

import com.lll.online.exam.base.BasePage;
import com.lll.online.exam.viewmodel.BaseVM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserEventLog分隔页查询接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEventLogRequestVM extends BasePage {
    private String userName;
    private Integer userId;
}
