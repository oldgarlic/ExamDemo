package com.lll.online.exam;

import com.lll.online.exam.mapper.UserMapper;
import com.lll.online.exam.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExamApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        System.out.println(userService.selectById(2));
    }

}
