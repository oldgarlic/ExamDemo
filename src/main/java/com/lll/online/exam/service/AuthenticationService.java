package com.lll.online.exam.service;

import com.lll.online.exam.entity.User;

/**
 * 认证服务类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public interface AuthenticationService {

    Boolean auth(User user,String username,String password);

    String pwdEncode(String password);

    String pwdDecode(String encodePwd);
}
