package com.lll.online.exam.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统配置
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
// TODO：这里有点小问题
@Configuration
@Component
@ConfigurationProperties(prefix = "system")
@Data
public class SystemConfig {
    private PasswordKeyConfig pwdKey;
    private List<String> securityIgnoreUrls;
}
