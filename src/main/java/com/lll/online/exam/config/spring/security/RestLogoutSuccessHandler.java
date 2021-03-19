package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.event.UserEvent;
import com.lll.online.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 用户登出成功处理器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/19
 */
@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Autowired
    UserService userService;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO：①：使用事件发布机制记录用户Log
        User userInfo =  (User)authentication.getPrincipal();
        if(userInfo!=null){
            com.lll.online.exam.entity.User user = userService.selectByUserName(userInfo.getUsername());
            UserEventLog userEventLog = new UserEventLog(user.getId(),user.getUserName(),user.getRealName(),new Date());
            userEventLog.setContent(user.getUserName()+"登出了在线考试系统");
            eventPublisher.publishEvent(new UserEvent(userEventLog));
        }
        RestUtil.response(response, SystemCode.OK);
    }
}
