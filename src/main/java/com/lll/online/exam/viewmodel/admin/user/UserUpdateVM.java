package com.lll.online.exam.viewmodel.admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户信息更新
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateVM {
    @NotBlank
    private String realName;
    @NotBlank
    private String phone;
}
