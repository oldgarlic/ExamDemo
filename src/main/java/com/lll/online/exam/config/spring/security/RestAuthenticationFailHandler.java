package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.base.SystemCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录认证失败处理器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/19
 */
@Component
public class RestAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // TODO：
        RestUtil.response(response, SystemCode.AuthError,exception.getMessage());
    }
}
