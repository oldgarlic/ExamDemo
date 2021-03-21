package com.lll.online.exam;

import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.mapper.UserEventLogMapper;
import com.lll.online.exam.mapper.UserMapper;
import com.lll.online.exam.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ExamApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserEventLogMapper userEventLogMapper;
    @Test
    void contextLoads() {
        UserEventLog userEventLog = new UserEventLog(2, "管理员", "sdshj", new Date());
        userEventLog.setContent("sdsads");
        userEventLogMapper.insert(userEventLog);
    }

    @Test
    void test1(){
        UserEventLog userEventLog = userEventLogMapper.selectById(1);
        System.out.println(userEventLog);
    }
}
