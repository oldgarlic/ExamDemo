package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.base.SystemCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthenticationException 和 anonymous和rememberMe情况下的AccessDeniedException 处理器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/15
 */
@Component
public class LoginAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    public LoginAuthenticationEntryPoint() {
        super("/api/user/login");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // TODO：result实例实例化为jsonStr返回
        RestUtil.response(response, SystemCode.UNAUTHORIZED);
    }
}
