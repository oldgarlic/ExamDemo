package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.event.UserEvent;
import com.lll.online.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 认证成功后处理器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/18
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO：①：获取用户信息→user，不懂认证成功后的信息存放；②：推送登录成功消息；③将前端锁需要的信息返回
        User userInfo = (User)authentication.getPrincipal();
        com.lll.online.exam.entity.User user = userService.selectByUserName(userInfo.getUsername());

        // TODO：消息推送  目的是为了业务解耦，本条业务不关心其他业务，它只需要将事件发布出去，其他的由监听器来决定。
        UserEventLog userEventLog = new UserEventLog(user.getId(),user.getUserName(),user.getRealName(),new Date());
        userEventLog.setContent(user.getUserName()+"登录了在线考试系统");
        eventPublisher.publishEvent(new UserEvent(userEventLog));


        com.lll.online.exam.entity.User newUser = new com.lll.online.exam.entity.User();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getImagePath());
        RestUtil.response(response, SystemCode.OK,newUser);
    }
}
