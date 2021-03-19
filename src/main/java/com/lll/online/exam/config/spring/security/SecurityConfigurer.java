package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.config.property.CookieConfig;
import com.lll.online.exam.config.property.SystemConfig;
import com.lll.online.exam.entity.enums.RoleEnum;
import com.sun.org.apache.xpath.internal.operations.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * 权限配置
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


    private RestAuthenticationSuccessHandler authenticationSuccessHandler;
    private RestAuthenticationFailHandler authenticationFailHandler;
    private RestAccessDeniedHandler accessDeniedHandler;
    private LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;
    private RestAuthenticationProvider authenticationProvider;
    private RestLogoutSuccessHandler logoutSuccessHandler;
    private RestUserDetailServiceImpl userDetailService;
    private SystemConfig systemConfig;

    @Autowired
    public SecurityConfigurer(RestAuthenticationSuccessHandler authenticationSuccessHandler, RestAuthenticationFailHandler authenticationFailHandler, RestAccessDeniedHandler accessDeniedHandler, LoginAuthenticationEntryPoint loginAuthenticationEntryPoint, RestAuthenticationProvider authenticationProvider, RestLogoutSuccessHandler logoutSuccessHandler, RestUserDetailServiceImpl userDetailService, SystemConfig systemConfig) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailHandler = authenticationFailHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.loginAuthenticationEntryPoint = loginAuthenticationEntryPoint;
        this.authenticationProvider = authenticationProvider;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userDetailService = userDetailService;
        this.systemConfig = systemConfig;
    }

    // TODO：① Exception处理器；②：成功失败处理器；③：过滤器、认证器、RememberMe
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 防止某些重要网页被其他网站框架导入
        http.headers().frameOptions().disable();
        // 忽视网页
        List<String> ignoreUrls = systemConfig.getSecurityIgnoreUrls();
        String[] arr = ignoreUrls.toArray(new String[ignoreUrls.size()]);
        http
                .addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(loginAuthenticationEntryPoint)
                .and().authenticationProvider(authenticationProvider)

                .authorizeRequests()
                .antMatchers(arr).permitAll()
                .antMatchers("/api/admin/**").hasRole(RoleEnum.ADMIN.getName())
                .antMatchers("/api/student/**").hasRole(RoleEnum.STUDENT.getName())
                .anyRequest().permitAll()

                // 为啥写下面
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)

                .and().formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailHandler)

                // invalidateHttpSession(true)→logout的时候Session无效
                .and().logout().logoutUrl("/api/user/logout").logoutSuccessHandler(logoutSuccessHandler).invalidateHttpSession(true)
                .and().rememberMe().key(CookieConfig.getName()).tokenValiditySeconds(CookieConfig.getInterval())
                .userDetailsService(userDetailService)
                .and().csrf().disable().cors();

    }

    // TODO：这个需要这么做嘛？？？
    @Bean
    public RestLoginAuthenticationFilter getAuthenticationFilter() throws Exception {
        // TODO：创建一个过滤器
        RestLoginAuthenticationFilter filter = new RestLoginAuthenticationFilter();
        filter.setUserDetailService(userDetailService);
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
