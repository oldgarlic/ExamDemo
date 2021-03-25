package com.lll.online.exam.service;


import com.lll.online.exam.entity.Message;
import java.util.List;

/**
 * (Message)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-21 00:02:48
 */
public interface MessageService extends BaseService<Message>{


    Integer unReadCount(Integer id);
}