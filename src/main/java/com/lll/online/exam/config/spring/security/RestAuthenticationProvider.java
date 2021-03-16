package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.RoleEnum;
import com.lll.online.exam.entity.enums.UserStatusEnum;
import com.lll.online.exam.service.AuthenticationService;
import com.lll.online.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 用户登录具体验证类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO：①：判断用户信息；②：判断用户状态；③：返回Token
        String userName = authentication.getName();
        String credentials = (String) authentication.getCredentials();

        User user = userService.selectByUserName(userName);
        if(user==null){
            throw new UsernameNotFoundException("用户名错误");
        }

        Boolean result = authenticationService.auth(user, userName, credentials);
        if(!result){
            throw  new BadCredentialsException("密码不正确");
        }

        if (UserStatusEnum.DISABLE.getCode() == user.getStatus()){
            throw new LockedException("用户已禁用");
        }

        ArrayList<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(RoleEnum.getRoleEnum(user.getRole()).getRoleName()));

        org.springframework.security.core.userdetails.User userInfo =
                new org.springframework.security.core.userdetails.User(userName,credentials,list);
        // 前后返回的不一样Token
        return new UsernamePasswordAuthenticationToken(userInfo,credentials,userInfo.getAuthorities());
    }

    // 这个无所谓改不改
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
