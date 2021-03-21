package com.lll.online.exam.config.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 七牛云数据库密钥
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String url;
}
