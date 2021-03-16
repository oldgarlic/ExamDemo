package com.lll.online.exam.service;

import com.lll.online.exam.entity.User;
import java.util.List;

/**
 * (User)表服务接口
 *
 * @author oldGarlic
 * @since 2021-03-16 20:25:10
 */
public interface UserService extends BaseService<User>{

    User selectByUserName(String userName);
}