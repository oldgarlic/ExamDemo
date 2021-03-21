package com.lll.online.exam.config.spring.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录bean
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationBean {
    private String userName;
    private String password;
    private boolean remember;
}
