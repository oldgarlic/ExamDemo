package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lll.online.exam.entity.Message;
import com.lll.online.exam.entity.MessageUser;
import com.lll.online.exam.mapper.MessageMapper;
import com.lll.online.exam.mapper.MessageUserMapper;
import com.lll.online.exam.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Message)表服务实现类
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:48
 */
@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    private MessageMapper messageMapper;
    private MessageUserMapper messageUserMapper;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper,MessageUserMapper messageUserMapper) {
        super(messageMapper);
        this.messageMapper = messageMapper;
        this.messageUserMapper = messageUserMapper;
    }

    @Override
    public Integer unReadCount(Integer id) {
        QueryWrapper<MessageUser> queryWrapper = new QueryWrapper<MessageUser>();
        queryWrapper.eq("receive_user_id",id).eq("readed",0);
        Integer count = messageUserMapper.selectCount(queryWrapper);
        return count;
    }
}