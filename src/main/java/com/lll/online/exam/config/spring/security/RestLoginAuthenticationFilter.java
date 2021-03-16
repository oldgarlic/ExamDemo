package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.config.property.CookieConfig;
import com.lll.online.exam.utility.JsonUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用户登录认证过滤器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public class RestLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public RestLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/user/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // TODO：从request中获取流，生成AuthenticationBean，②：是否使用rememberMe功能，③：调用AuthenticationManager
        InputStream ips = request.getInputStream();
        AuthenticationBean authenticationBean = JsonUtil.toJsonObject(ips, AuthenticationBean.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authenticationBean.getUsername(), authenticationBean.getPassword());
        setDetails(request,token);
        // 是否使用RememberMeService
        request.setAttribute(TokenBasedRememberMeServices.DEFAULT_PARAMETER,authenticationBean.isRemember());
        return this.getAuthenticationManager().authenticate(token);
    }

    // 设置RememberMeService
    public void setUserDetailService(UserDetailsService userDetailService){
        RestTokenBasedRememberMeServices restTokenBasedRememberMeServices = new RestTokenBasedRememberMeServices(CookieConfig.getName(),userDetailService);
        restTokenBasedRememberMeServices.setTokenValiditySeconds(CookieConfig.getInterval());
        setRememberMeServices(restTokenBasedRememberMeServices);
    }


    // 照抄 UsernamePasswordAuthenticationFilter方法
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
