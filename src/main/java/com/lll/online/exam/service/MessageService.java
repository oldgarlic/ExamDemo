package com.lll.online.exam.service;


import com.lll.online.exam.base.PageResult;
import com.lll.online.exam.entity.Message;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.viewmodel.admin.message.MessagePageRequestVM;
import com.lll.online.exam.viewmodel.admin.message.MessageRequestVM;
import com.lll.online.exam.viewmodel.admin.message.MessageResponseVM;

import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:48
 */
public interface MessageService extends BaseService<Message>{


    Integer unReadCount(Integer id);

    PageResult<MessageResponseVM> pageList(MessagePageRequestVM model);

    void sendMessage(MessageRequestVM model, User currentUser);

    PageResult<com.lll.online.exam.viewmodel.student.message.MessageResponseVM> studentPage(com.lll.online.exam.viewmodel.student.message.MessageRequestVM messageRequestVM);

    void readMessage(Integer id, User user);
}