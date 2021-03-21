package com.lll.online.exam.listener;

import com.lll.online.exam.event.UserEvent;
import com.lll.online.exam.service.UserEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 用户事件监听器
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/18
 */
@Component
public class UserEventLogListener implements ApplicationListener<UserEvent> {

    @Autowired
    private UserEventLogService userEventLogService;

    @Override
    public void onApplicationEvent(UserEvent userEvent) {
        // TODO：插入不为null的数据，为啥呢？
        userEventLogService.insert(userEvent.getUserEventLog());
    }
}
