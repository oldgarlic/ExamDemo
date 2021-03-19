package com.lll.online.exam.event;

import com.lll.online.exam.entity.UserEventLog;
import org.springframework.context.ApplicationEvent;

/**
 * 用户操作事件
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/18
 */
public class UserEvent  extends ApplicationEvent {
    private UserEventLog userEventLog;

    public UserEvent(UserEventLog userEventLog) {
        super(userEventLog);
        this.userEventLog = userEventLog;
    }

    public UserEventLog getUserEventLog() {
        return userEventLog;
    }
}
