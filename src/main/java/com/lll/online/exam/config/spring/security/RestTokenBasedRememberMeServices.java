package com.lll.online.exam.config.spring.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.servlet.http.HttpServletRequest;

/**
 * RememberMeService
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public class RestTokenBasedRememberMeServices extends TokenBasedRememberMeServices {


    public RestTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    // 判断是否使用自动登录
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        return (Boolean) request.getAttribute(DEFAULT_PARAMETER);
    }
}
