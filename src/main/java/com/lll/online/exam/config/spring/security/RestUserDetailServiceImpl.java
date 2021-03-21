package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.entity.User;
import com.lll.online.exam.entity.enums.RoleEnum;
import com.lll.online.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Spring Security数据库用户查询类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
@Component
public class RestUserDetailServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public RestUserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.selectByUserName(username);

        if(user==null){
            throw new UsernameNotFoundException("用户不存在");
        }

        ArrayList<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        // TODO：数据库中保存角色为 Integer，GrantedAuthority需要为String
        list.add(new SimpleGrantedAuthority(RoleEnum.getRoleEnum(user.getRole()).getRoleName()));

        org.springframework.security.core.userdetails.User userInfo =
                new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),list);

        return userInfo;
    }
}
