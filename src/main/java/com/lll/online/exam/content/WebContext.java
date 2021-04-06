package com.lll.online.exam.content;

import com.lll.online.exam.entity.User;
import com.lll.online.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 用户信息上下文
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
@Component
public class WebContext {
    // TODO：依据successfulAuthentication#SecurityContextHolder.getContext().setAuthentication(authResult);
    // TODO: 我们可以从上下中获取用户信息，从SecurityContextHolder取出，存放到RequestContextHolder中
    // TODO：SecurityContextHolder的存放是用ThreadLocal，RequestContextHolder的存放是用HashMap

    public final static String USER_KEY = "USER_KEY";
    @Autowired
    private UserService userService;

    /**
    * @Description: 获取当前用户信息
    * @Param:
    * @return:
    * @Date: 2021/3/20
    */
    public User getCurrentUser(){
        User user = (User) RequestContextHolder.getRequestAttributes().getAttribute(USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(user!=null){
            return user;
        }else{
            org.springframework.security.core.userdetails.User userInfo = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(userInfo==null){
                return null;
            }
            String username = userInfo.getUsername();
            User newUser = userService.selectByUserName(username);
            if(newUser!=null) {
                setUser(newUser);
            }
            return newUser;
        }
    }

    public void setUser(User user){
        // TODO：key，user，作用范围
        RequestContextHolder.getRequestAttributes().setAttribute(USER_KEY,user, RequestAttributes.SCOPE_REQUEST);
    }
}
