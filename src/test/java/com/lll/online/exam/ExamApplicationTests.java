package com.lll.online.exam;

import com.lll.online.exam.entity.Subject;
import com.lll.online.exam.entity.UserEventLog;
import com.lll.online.exam.mapper.UserEventLogMapper;
import com.lll.online.exam.mapper.UserMapper;
import com.lll.online.exam.service.UserService;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.education.SubjectResponseVM;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class ExamApplicationTests {

    protected final static ModelMapper modelMapper = ModelMapperUtil.instance();

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserEventLogMapper userEventLogMapper;

    @Test
    void test2(){
        Subject subject = new Subject(1,"sads",2,"323",1,false);
        SubjectResponseVM map = modelMapper.map(subject, SubjectResponseVM.class);
        System.out.println(map);
    }

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
