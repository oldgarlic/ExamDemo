package com.lll.online.exam.viewmodel.admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 修改/添加用户接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateVM {
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @NotBlank(message = "密码不能为空")
    private String password;
    private Integer userLevel;
    private Integer status;
    private Integer role;

    private Integer age;
    private Date birthDay;
    private String phone;
    private Integer sex;
}
