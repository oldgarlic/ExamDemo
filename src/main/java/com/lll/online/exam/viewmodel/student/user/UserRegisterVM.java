package com.lll.online.exam.viewmodel.student.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户注册接受类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVM {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotNull
    private Integer userLevel;
}
