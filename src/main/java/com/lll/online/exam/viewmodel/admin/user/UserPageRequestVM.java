package com.lll.online.exam.viewmodel.admin.user;

import com.lll.online.exam.base.BasePage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户查询接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageRequestVM extends BasePage {
    private String userName;
    private Integer role;
}
