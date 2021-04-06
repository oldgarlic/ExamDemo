package com.lll.online.exam.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.Message;
import com.lll.online.exam.entity.MessageUser;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.mapper.MessageMapper;
import com.lll.online.exam.mapper.MessageUserMapper;
import com.lll.online.exam.service.MessageService;
import com.lll.online.exam.service.UserService;
import com.lll.online.exam.utility.DateTimeUtil;
import com.lll.online.exam.utility.ModelMapperUtil;
import com.lll.online.exam.viewmodel.admin.message.MessagePageRequestVM;
import com.lll.online.exam.viewmodel.admin.message.MessageRequestVM;
import com.lll.online.exam.viewmodel.admin.message.MessageResponseVM;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ModelMapper modelMapper = ModelMapperUtil.instance();
    private UserService userService;

    @Autowired
    public MessageServiceImpl(UserService userService,MessageMapper messageMapper,MessageUserMapper messageUserMapper) {
        super(messageMapper);
        this.messageMapper = messageMapper;
        this.messageUserMapper = messageUserMapper;
        this.userService = userService;
    }

    @Override
    public Integer unReadCount(Integer id) {
        QueryWrapper<MessageUser> queryWrapper = new QueryWrapper<MessageUser>();
        queryWrapper.eq("receive_user_id",id).eq("readed",0);
        Integer count = messageUserMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public PageResult<MessageResponseVM> pageList(MessagePageRequestVM model) {
        Page<Message> messagePage = new Page<>(model.getPageIndex(), model.getPageSize());
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isBlank(model.getSendUserName())){
            queryWrapper.eq("send_user_name",model.getSendUserName());
        }
        IPage<Message> messageIPage = messageMapper.selectPage(messagePage, queryWrapper);

        List<Integer> messageIds = messageIPage.getRecords().stream().map(t -> t.getId()).collect(Collectors.toList());
        List<MessageUser> messageUsers = selectByIds(messageIds);

        List<MessageResponseVM> data = messageIPage.getRecords().stream().map(t -> {
            MessageResponseVM messageResponseVM = modelMapper.map(t, MessageResponseVM.class);
            messageResponseVM.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));

            String receives = messageUsers.stream().filter(d -> d.getMessageId().equals(t.getId())).map(s -> s.getReceiveUserName())
                    .collect(Collectors.joining(","));
            messageResponseVM.setReceives(receives);
            return messageResponseVM;
        }).collect(Collectors.toList());



        return new PageResult<>(data,messageIPage.getTotal(),messageIPage.getCurrent());
    }

    @Transactional
    @Override
    public void sendMessage(MessageRequestVM model, User currentUser) {
        // TODO：涉及到的表有：message，message_user
        Date now = new Date();
        Message message = modelMapper.map(model, Message.class);
        message.setCreateTime(now);
        message.setReadCount(0);
        message.setSendRealName(currentUser.getRealName());
        message.setSendUserId(currentUser.getId());
        message.setReceiveUserCount(model.getReceiveUserIds().size());
        message.setSendUserName(currentUser.getUserName());
        messageMapper.insert(message);

        List<User> users = userService.selectUserByIds(model.getReceiveUserIds());
        List<MessageUser> messageUsers = users.stream().map(t -> {
            MessageUser messageUser = new MessageUser();
            messageUser.setCreateTime(now);
            messageUser.setMessageId(message.getId());
            messageUser.setReaded(false);
            messageUser.setReceiveRealName(t.getRealName());
            messageUser.setReceiveUserId(t.getId());
            messageUser.setReceiveUserName(t.getUserName());
            return messageUser;
        }).collect(Collectors.toList());

        // TODO：前面都没有问题，后面开始
        // 批量插入
        insertList(messageUsers);

    }

    @Override
    public PageResult<com.lll.online.exam.viewmodel.student.message.MessageResponseVM> studentPage(com.lll.online.exam.viewmodel.student.message.MessageRequestVM messageRequestVM) {
        Page<MessageUser> messageUserPage = new Page<>(messageRequestVM.getPageIndex(), messageRequestVM.getPageSize());
        QueryWrapper<MessageUser> queryWrapper = new QueryWrapper<>();
        if(messageRequestVM.getReceiveUserId()!=null){
            queryWrapper.eq("receive_user_id",messageRequestVM.getReceiveUserId());
        }
        IPage<MessageUser> messageUserIPage = messageUserMapper.selectPage(messageUserPage, queryWrapper);

        List<com.lll.online.exam.viewmodel.student.message.MessageResponseVM> data = messageUserIPage.getRecords().stream().map(t -> {
            com.lll.online.exam.viewmodel.student.message.MessageResponseVM vm = modelMapper.map(t, com.lll.online.exam.viewmodel.student.message.MessageResponseVM.class);
            Message message = messageMapper.selectById(t.getMessageId());
            vm.setContent(message.getContent());
            vm.setCreateTime(DateTimeUtil.dateFormat(t.getCreateTime()));
            vm.setSendUserName(message.getSendUserName());
            vm.setTitle(message.getTitle());
            return vm;
        }).collect(Collectors.toList());

        return new PageResult<>(data,messageUserIPage.getTotal(),messageUserIPage.getTotal());
    }
    @Transactional
    @Override
    public void readMessage(Integer id, User user) {
        // TODO：给t_message和t_message_user表进行数据更新
        // TODO：t_message_user更新readed和read_time字段，t_message更新read_count字段
        Date now = new Date();
        UpdateWrapper<MessageUser> messageUserUpdateWrapper = new UpdateWrapper<>();
        messageUserUpdateWrapper.set("readed",true).set("read_time",now).eq("receive_user_id",user.getId()).eq("id",id);
        messageUserMapper.update(null,messageUserUpdateWrapper);


        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("id",messageUserMapper.selectById(id).getMessageId()).setSql("read_count = read_count+1");
        messageMapper.update(null,messageUpdateWrapper);
    }

    private void insertList(List<MessageUser> messageUsers){
        messageUserMapper.insertList(messageUsers);
    }


    private List<MessageUser> selectByIds(List<Integer> ids){
        QueryWrapper<MessageUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("message_id",ids);
        List<MessageUser> messageUsers = messageUserMapper.selectList(queryWrapper);
        return messageUsers;
    }
}