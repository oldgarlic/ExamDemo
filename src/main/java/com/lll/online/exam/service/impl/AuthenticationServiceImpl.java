package com.lll.online.exam.service.impl;

import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.config.property.SystemConfig;
import com.lll.online.exam.entity.User;
import com.lll.online.exam.service.AuthenticationService;
import com.lll.online.exam.utility.RsaUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户认证服务实现类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private SystemConfig systemConfig;


    @Override
    public Boolean auth(User user, String username, String password) {
        // TODO：这里需要一个加密解密工具类
        // TODO：①：判断username；②判断password；③：密码比较
        if(username==null){return false;}
        String encodePassword = user.getPassword();
        if(encodePassword==null||encodePassword.length()==0){
            return false;
        }
        // 加密或者解密后进行对比
        String pwd = pwdDecode(password);
        return pwd.equals(encodePassword);
    }


    // RSA加密解析需要一个publicKey和privateKey
    @Override
    public String pwdEncode(String password) {
        return RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(),password);
    }

    @Override
    public String pwdDecode(String encodePwd) {
        return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(),encodePwd);
    }

}
