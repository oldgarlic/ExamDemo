package com.lll.online.exam.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * RSA加密解析配置类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
@Data
public class PasswordKeyConfig {
    private String publicKey;
    private String privateKey;

}
